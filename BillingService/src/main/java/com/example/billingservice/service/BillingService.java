package com.example.billingservice.service;

import com.example.billingservice.entity.Billing;

public interface BillingService {
    Billing findById(int billId);
}
