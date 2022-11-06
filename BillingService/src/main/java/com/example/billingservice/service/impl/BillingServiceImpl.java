package com.example.billingservice.service.impl;

import com.example.billingservice.entity.Billing;
import com.example.billingservice.repository.BillingRepository;
import com.example.billingservice.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingRepository billingRepository;

    @Override
    public Billing findById(int billId) {
        return billingRepository.findById(billId).get();
    }
}
