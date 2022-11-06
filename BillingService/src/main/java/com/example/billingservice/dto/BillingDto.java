package com.example.billingservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillingDto {
    private int billId;
    private String name;
    private double price;
    private PassengerDto passenger;
}
