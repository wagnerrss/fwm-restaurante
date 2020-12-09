package com.fwm.restaurante.config.tenant;

import com.fwm.restaurante.config.cache.DataCache;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


/**
 * Spring multi-tenancy implementation.
 * <p>
 * Initializing the tenant data sources.
 *
 * @author Ranjith Manickam
 * @since 1.0
 */
@Configuration
public class TenantConfiguration {

    @Autowired
    private DataSourceProperties properties;

    private Log log = LogFactory.getLog(TenantConfiguration.class);

    DataSource defaultDataSource;

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        try {
            defaultDataSource = getDefaultDataSource();

            Map<TenantInfo, List<TenantProperty>> tenantInfos = getTenantInfo(defaultDataSource);

            Map<Object, Object> tenantDataSources = new HashMap<>();

            if (tenantInfos != null) {
                for (TenantInfo tenantInfo : tenantInfos.keySet()) {
                    if (tenantInfo.isActive()) {
                        HikariDataSource hikariDataSource = new HikariDataSource();

                        hikariDataSource.setDriverClassName(properties.getDriverClassName());

                        hikariDataSource.setMaximumPoolSize(1);
                        hikariDataSource.setUsername(properties.getUsername());
                        hikariDataSource.setPassword(properties.getPassword());
                        hikariDataSource.setJdbcUrl(tenantInfo.getAccessUrl());


                        tenantDataSources.put(tenantInfo, hikariDataSource);
                    }
                }
            }

            final TenantDataSource dataSource = new TenantDataSource();

            // default data source
            dataSource.setDefaultTargetDataSource(defaultDataSource);

            // tenant data source
            dataSource.setTargetDataSources(tenantDataSources);

            // Call this to finalize the initialization of the data source.
            dataSource.afterPropertiesSet();

            return dataSource;
        } catch (Exception ex) {
            log.error("Error occured while building data sources", ex);
        }
        return null;
    }

    /**
     * method to get default data source
     *
     * @return
     */
    private DataSource getDefaultDataSource() {
        if (defaultDataSource == null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setMaximumPoolSize(1);
            hikariConfig.setUsername(properties.getUsername());
            hikariConfig.setPassword(properties.getPassword());
            hikariConfig.setDriverClassName(properties.getDriverClassName());
            hikariConfig.setJdbcUrl(properties.getUrl());

            return new HikariDataSource(hikariConfig);
        }

        return defaultDataSource;
    }

    /**
     * method to get tenant info
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    private Map<TenantInfo, List<TenantProperty>> getTenantInfo(DataSource dataSource) throws Exception {
        final String TENANT_INFO = "SELECT TENANT_ID, NAME, 'jdbc:postgresql://'|| IP_ADDRESS || ':5432/'|| DATABASE ACCESS_URL FROM CLIENT";
        Map<TenantInfo, List<TenantProperty>> tenantInfos = new HashMap<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(TENANT_INFO);) {

            if (result != null) {
                while (result.next()) {

                    TenantInfo tenantInfo = new TenantInfo(result.getString("TENANT_ID"));
                    tenantInfo.setTenantName(result.getString("NAME"));

                    String accessUrl = result.getString("ACCESS_URL");
                    tenantInfo.setAccessUrl(accessUrl);

                    System.out.println(accessUrl);

                    tenantInfo.setCreatedDateTime(new Date());
                    tenantInfo.setLastUpdatedDateTime(new Date());
                    tenantInfo.setExpiryDateTime(null);
                    tenantInfo.setActive(true);

                    tenantInfos.put(tenantInfo, null);
                }
            }

            result.close();
            statement.close();
            connection.close();
        }

        for (TenantInfo tenantInfo : tenantInfos.keySet()) {
            List<TenantProperty> tenantProperties = getTenantProperties(dataSource, tenantInfo);
            tenantInfos.put(tenantInfo, tenantProperties);
        }

        // adding the tenant informations to cache
        DataCache.getInstance(TenantConstants.TENANT_COMMON).put(TenantConstants.TENANTS_INFO, tenantInfos);

        return tenantInfos;
    }

    /**
     * method to get tenant properties
     *
     * @param dataSource
     * @param tenantInfo
     * @return
     * @throws Exception
     */
    private List<TenantProperty> getTenantProperties(DataSource dataSource, TenantInfo tenantInfo) throws Exception {
        final String TENANT_PROPERTY = "SELECT TENANT_ID FROM CLIENT WHERE TENANT_ID = '" + tenantInfo.getTenantId() + "'";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TENANT_PROPERTY);) {

            ResultSet result = statement.executeQuery();

            List<TenantProperty> tenantProperties = null;
            if (result != null) {
                tenantProperties = new ArrayList<>();
                while (result.next()) {
                    TenantProperty tenantProperty = new TenantProperty();
                    tenantProperty.setPropertyName(result.getString("TENANT_ID"));
                    tenantProperty.setPropertyValue(result.getString("TENANT_ID"));
                    tenantProperty.setTenantInfo(tenantInfo);

                    tenantProperties.add(tenantProperty);
                }
            }

            result.close();
            statement.close();
            connection.close();

            return tenantProperties;
        }
    }

}
