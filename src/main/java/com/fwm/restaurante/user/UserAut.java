/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fwm.restaurante.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Felipe
 */
@Data
public class UserAut implements UserDetails, Serializable {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String login;
    private String nome;
    private String email;
    private String senha = encoder.encode("123");

    // token jwt
    private String token;

    public static void main(String[] args) {
    }

    public UserAut(String login) {
        this.login = login;
    }

    public static UserAut create(UserAut user, String token) {
        ModelMapper modelMapper = new ModelMapper();
        UserAut dto = modelMapper.map(user, UserAut.class);
        dto.token = token;
        return dto;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
