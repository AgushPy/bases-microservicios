package com.tutorial.bikeservice.service;

import com.tutorial.bikeservice.repository.BikeRepository;
import com.tutorial.bikeservice.entity.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {

    @Autowired
    private BikeRepository bikeRepository;

    public List<Bike> getAll(){
        return bikeRepository.findAll();
    }

    public Bike getBikeById(int id){
        return bikeRepository.findById(id).orElse(null);
    }

    public Bike save(Bike Bike){
        Bike BikeNew = bikeRepository.save(Bike);
        return BikeNew;
    }

    public List<Bike> byUserId(int id){
        return bikeRepository.findByUserId(id);
    }
}
