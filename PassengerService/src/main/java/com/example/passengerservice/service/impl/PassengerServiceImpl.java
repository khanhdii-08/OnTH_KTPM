package com.example.passengerservice.service.impl;

import com.example.passengerservice.entity.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public Passenger findById(int passengerId) {
        return passengerRepository.findById(passengerId).get();
    }

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public boolean delete(int passengerId) {
        try {
            passengerRepository.deleteById(passengerId);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
