package com.tutorial.userservice.feignclients;

import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bike-service")
//@RequestMapping("/bike")
public interface BikeFeignClient {

    @PostMapping("/bike/save")
    Bike save(@RequestBody Bike bike);


    @GetMapping("/bike/byuser/{userId}")
    List<Bike> getBikes(@PathVariable("userId") int userId);

}
