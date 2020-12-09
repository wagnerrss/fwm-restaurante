package com.fwm.restaurante.user;

import com.fwm.restaurante.config.AbstractAPIService;
import com.fwm.restaurante.config.tenant.TenantConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring multi-tenancy implementation.
 * <p>
 * Client test service implementation.
 *
 * @author Ranjith Manickam
 * @since 1.0
 */
@Controller
public class UserAPIService extends AbstractAPIService {

    @Autowired
    private UserRepository repository;

    private HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Transactional
    @CrossOrigin
    @GetMapping("api/users")
    public ResponseEntity<List<User>> get() {
        try {
            return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            HttpHeaders headers = getResponseHeaders(ex);
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @CrossOrigin
    @PostMapping("api/loginUser")
    public ResponseEntity<Map> userLogin(@RequestBody Map value) {
        try {
            System.out.println(value.get("USER") + " - " + value.get("PASSWORD"));
            return new ResponseEntity<>(repository.findLogin((String) value.get("USER"), (String) value.get("PASSWORD")), HttpStatus.OK);
        } catch (Exception ex) {
            HttpHeaders headers = getResponseHeaders(ex);
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    TenantConfiguration tenantConfiguration;

    @Autowired
    UserDetailsService userDetailsService;

    @CrossOrigin
    @GetMapping("api/ip")
    public ResponseEntity<Map> getIp() {
        Map map = new LinkedHashMap();

        try {
            if (request != null) {
                map.put("X-Forwarded-For", request.getHeader("X-FORWARDED-FOR"));
                map.put("LocalAddr", request.getLocalAddr());
                map.put("RemoteAddr", request.getRemoteAddr());
            }
        } catch (Exception ex) {
            map.clear();
            map.put("IP", "Erro " + ex);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
