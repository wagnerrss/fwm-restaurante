package com.fwm.restaurante.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer company;
    private Integer employee;
    private Date saleDate;
    private Float saleValue;
    private Float saleDiscount;
    private Float saleAddition;
    private Float saleTotal;
    private String saleComments;

}
