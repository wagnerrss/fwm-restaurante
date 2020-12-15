package com.fwm.restaurante.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "system_user")
@Data
public class SystemUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String type;
}
