package com.example.passengerservice.service;

import com.example.passengerservice.entity.Passenger;

import java.util.List;

public interface PassengerService {
    Passenger findById(int passengerId);
    List<Passenger> findAll();
    Passenger save(Passenger passenger);
    boolean delete(int passengerId);
}
