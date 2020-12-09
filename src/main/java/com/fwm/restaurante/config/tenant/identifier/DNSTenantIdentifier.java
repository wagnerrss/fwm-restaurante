package com.fwm.restaurante.config.tenant.identifier;

import com.fwm.restaurante.config.tenant.TenantHelper;
import com.fwm.restaurante.config.tenant.TenantInfo;
import com.fwm.restaurante.exception.TenantNotExistsException;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring multi-tenancy implementation.
 *
 * DNS tenant identifier implementation to identify the tenants from domain
 * name.
 *
 * ex: ranjith.ranmanic.in -> tenant id: ranjith
 *
 * @author Ranjith Manickam
 * @since 1.0
 */
class DNSTenantIdentifier extends TenantIdentifier {

    /**
     * {@inheritDoc}
     */
    @Override
    public TenantInfo identifyTenant(HttpServletRequest request) throws TenantNotExistsException {

        String serverName = request.getServerName();

        String tenantId = serverName.split("\\.")[0].toUpperCase();
        TenantInfo tenantInfo = TenantHelper.getTenantInfo(tenantId);

        if (tenantInfo == null) {
            throw new TenantNotExistsException(tenantId);
        }

        return tenantInfo;
    }

}
