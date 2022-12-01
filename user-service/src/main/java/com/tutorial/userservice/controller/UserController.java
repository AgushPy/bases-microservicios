package com.tutorial.userservice.controller;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAll();
        if(users.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id){
        User user =  userService.getUserById(id);
        if(user == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    //Se debe crear un DTO
    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User userNew =  userService.save(user);
        return ResponseEntity.ok(userNew);
    }

    @CircuitBraker(name= "carsCB",fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId){
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBraker(name= "carsCB",fallbackMethod = "fallBackSaveCar")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId,@RequestBody Car car){
        if(userService.getUserById(userId) == null){
            return  ResponseEntity.notFound().build();
        }
        Car carNew = userService.saveCar(userId,car);
        return ResponseEntity.ok(carNew);
    }

    @CircuitBraker(name= "bikesCB",fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId){
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }

    @CircuitBraker(name= "bikesCB",fallbackMethod = "fallBackSaveBikes")
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId,@RequestBody Bike bike){
        if(userService.getUserById(userId) == null){
            return  ResponseEntity.notFound().build();
        }
        Bike bikeNew = userService.saveBike(userId,bike);
        return ResponseEntity.ok(bikeNew);
    }

    @CircuitBraker(name= "allCB",fallbackMethod = "fallBackGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable("userId") int userId){
        Map<String,Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int userId,RuntimeException e){
        return new ResponseEntity("El Usuario "+userId+" tiene los coches en el taller",HttpStatus.OK);
    }

    private ResponseEntity<List<Car>> fallBackSaveCar(@PathVariable("userId") int userId,@RequestBody Car car,RuntimeException e){
        return new ResponseEntity("El Usuario "+userId+" no tiene dinero para autos",HttpStatus.OK);
    }
    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int userId,RuntimeException e){
        return new ResponseEntity("El Usuario "+userId+" tiene las motos en el taller",HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackSaveBikes(@PathVariable("userId") int userId,@RequestBody Bike bike,RuntimeException e){
        return new ResponseEntity("El Usuario "+userId+" no tiene dinero para motos",HttpStatus.OK);
    }

    private ResponseEntity<Map<String,Object>> fallBackGetAll(@PathVariable("userId") int userId, RuntimeException e){
        return new ResponseEntity("El Usuario "+userId+" tiene los vehiculos en el taller",HttpStatus.OK);

    }
}
