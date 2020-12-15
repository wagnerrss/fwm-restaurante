package com.fwm.restaurante.domain;

import lombok.Data;

@Data
public class Login extends SystemUser{
    String token;
}
