package com.fwm.restaurante.config.security;


import com.fwm.restaurante.config.tenant.TenantContext;
import com.fwm.restaurante.config.tenant.TenantHelper;
import com.fwm.restaurante.config.tenant.TenantInfo;
import com.fwm.restaurante.user.UserAut;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TenantInfo tenantInfo = TenantHelper.getTenantInfo(username);

        if (!tenantInfo.isActive()){
            throw new UsernameNotFoundException("user not found");
        }
        TenantContext.setCurrentTenant(tenantInfo);

        return new UserAut(username);
    }
}
