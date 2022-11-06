package com.example.billingservice.controller;

import com.example.billingservice.dto.BillingDto;
import com.example.billingservice.dto.PassengerDto;
import com.example.billingservice.entity.Billing;
import com.example.billingservice.service.BillingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@RestController
@RequestMapping("/billings")
public class BillingRestApi {

    @Autowired
    private BillingService billingService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/{billId}")
    @Retry(name = "passenger")
//    @CircuitBreaker(name = "passenger")
//    @RateLimiter(name = "passenger")
    public BillingDto findById(@PathVariable int billId){
        try {
            System.out.println(Thread.currentThread().getName() + "...running  " +
                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            Billing billing = billingService.findById(billId);

            int passengerId = billing.getPassengerId();
            PassengerDto passengerDto = restTemplate.exchange("http://localhost:8002/passengers".concat("/").concat(String.valueOf(passengerId)),
                    HttpMethod.GET,
                    entity,
                    PassengerDto.class
            ).getBody();
        BillingDto billingDto  = BillingDto.builder()
                    .billId(billing.getBillId())
                    .name(billing.getName())
                    .price(billing.getPrice())
                    .passenger(passengerDto).build();


        return billingDto;

    }
}
