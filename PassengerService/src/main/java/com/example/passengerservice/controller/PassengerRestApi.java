package com.example.passengerservice.controller;

import com.example.passengerservice.entity.Passenger;
import com.example.passengerservice.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/passengers")
public class PassengerRestApi {
    private static final String REDIS_CACHE_VALUE = "passenger";
    @Autowired
    private PassengerService passengerService;

    @Autowired
    private RedisTemplate template;


    @GetMapping
    public List<Passenger> findAll() {
        List<Passenger> ls =(List<Passenger>) template.opsForHash().values(REDIS_CACHE_VALUE);
        if(ls.size() > 0){
            System.out.println("get from redis");
            return ls;
        }
        System.out.println("get from db");
        ls = passengerService.findAll();
        if(ls.size() > 0) {
            Map<String, Passenger> map = new HashMap<>();
            for(Passenger p : ls) {
                map.put(String.valueOf(p.getPassengerId()), p);
            }
            if(map.size() > 0)
                template.opsForHash().putAll(REDIS_CACHE_VALUE, map);
        }
        return ls;
    }


    @GetMapping("/{passengerId}")
    public Passenger findById(@PathVariable int passengerId){
        Passenger passenger =  null;
        passenger = (Passenger) template.opsForHash().get(REDIS_CACHE_VALUE, passengerId);
        if(passenger != null){
            System.out.println("get from redis");
            return passenger;
        }
        System.out.println("get from db");
        passenger = passengerService.findById(passengerId);
        if(passenger != null)
            template.opsForHash().put(REDIS_CACHE_VALUE, passenger.getPassengerId(), passenger);
        return passenger;
    }


    @PostMapping
    public Passenger save(@RequestBody Passenger passenger) {
        Passenger temp = passengerService.save(passenger);
        template.opsForHash().put(REDIS_CACHE_VALUE, temp.getPassengerId(), passenger);
        return temp;
    }

    @DeleteMapping("/{passengerId}")
    public boolean delete(@PathVariable int passengerId) {
        try {
            template.opsForHash().delete(REDIS_CACHE_VALUE, passengerId);
            passengerService.delete(passengerId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
