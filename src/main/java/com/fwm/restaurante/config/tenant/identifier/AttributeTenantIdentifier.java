package com.fwm.restaurante.config.tenant.identifier;

import com.fwm.restaurante.config.properties.ApplicationProperties;
import com.fwm.restaurante.config.tenant.TenantHelper;
import com.fwm.restaurante.config.tenant.TenantInfo;
import com.fwm.restaurante.exception.TenantNotExistsException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * Spring multi-tenancy implementation.
 * 
 * Attribute tenant identifier implementation to identify tenants based on URL attribute.
 * 
 * ex: https://localhost/api?tenant=ranjith -> tenant id: ranjith
 * 
 * @author Ranjith Manickam
 * @since 1.0
 */
class AttributeTenantIdentifier extends TenantIdentifier {

	/** {@inheritDoc} */
	@Override
	public TenantInfo identifyTenant(HttpServletRequest request) throws TenantNotExistsException {

		String param = (String) ApplicationProperties.get("attribute.tenant.identifier.param");
		String tenantId = request.getParameter(param);

		if (StringUtils.isEmpty(tenantId)) {
			tenantId = (String) request.getAttribute(param);
		}

		TenantInfo tenantInfo = TenantHelper.getTenantInfo(tenantId);

		if (tenantInfo == null) {
			throw new TenantNotExistsException(tenantId);
		}

		request.setAttribute(param, tenantId);
		return tenantInfo;
	}

}