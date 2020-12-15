package com.fwm.restaurante.service;


import com.fwm.restaurante.config.tenant.TenantContext;
import com.fwm.restaurante.config.tenant.TenantHelper;
import com.fwm.restaurante.config.tenant.TenantInfo;
import com.fwm.restaurante.domain.Login;
import com.fwm.restaurante.domain.SystemUser;
import com.fwm.restaurante.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public Login findUser(Map login) {
        String token = conectaPeloGrupo(login.get("tenant_id").toString(), userDetailsService);

        SystemUser s = loginRepository.findByUsernamePassword(login.get("username").toString(), login.get("password").toString());

        Login l =  new Login();
        if (s != null) {
            l.setId(s.getId());
            l.setUsername(s.getUsername());
            l.setPassword(s.getPassword());
            l.setEmail(s.getEmail());
            l.setType(s.getType());
            l.setToken(token);
        }

        return l;
    }

    public String conectaPeloGrupo(String grupo, UserDetailsService userDetailsService) {
        //Conexão com o grupo passado no parâmetro request...
        TenantHelper tenantHelper = new TenantHelper();

        TenantInfo tenantInfo = tenantHelper.getTenantInfo(grupo);

        if (!tenantInfo.isActive()) {
            return "";
        }

        TenantContext.setCurrentTenant(tenantInfo);

        //encontra o usuário pelo grupo
        UserDetails userDetails = userDetailsService.loadUserByUsername(grupo);

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        // Salva o Authentication no contexto do Spring
        SecurityContextHolder.getContext().setAuthentication(auth);

        return tenantInfo.getToken();
    }


}
