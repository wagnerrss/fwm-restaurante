package com.fwm.restaurante.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class SaleProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer sale;
    private Integer product;
    private Float saleProductCost;
    private Float saleProductValue;
    private Float saleProductDiscoutn;
    private Float saleProductAddition;
}