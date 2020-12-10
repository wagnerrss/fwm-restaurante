package com.fwm.restaurante.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class SalePayment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer sale;
    private Integer payment;
    private Float paymentValue;
    private Float paymentDiscount;
    private Float paymentAddition;
}

