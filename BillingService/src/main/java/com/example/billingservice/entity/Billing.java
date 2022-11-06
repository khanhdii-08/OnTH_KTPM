package com.example.billingservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billId;
    private String name;
    private double price;
    private int passengerId;
}
