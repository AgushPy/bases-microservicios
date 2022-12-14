package com.tutorial.userservice.feignclients;

import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bike-service",url = "http://localhost:8003/bike")
//@RequestMapping("/car")
public interface BikeFeignClient {

    @PostMapping()
    Bike save(@RequestBody Bike bike);


    @GetMapping("/byuser/{userId}")
    List<Bike> getBikes(@PathVariable("userId") int userId);

}
