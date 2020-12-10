package com.fwm.restaurante.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String fantasy;
    private String cnpj;
    private String ie;
    private String address;
    private String number;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;
    private String zipcode;
    private String fone;
    private String mail;

}
