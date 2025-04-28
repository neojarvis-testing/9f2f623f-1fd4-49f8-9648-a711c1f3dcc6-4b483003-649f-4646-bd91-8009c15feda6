package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Food;
import com.examly.springapp.service.FoodService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/food")
public class FoodController {

    @Autowired
    FoodService foodService;

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Food> addFood(@RequestBody Food food) {
        Food createdFood = foodService.addFood(food);
        return ResponseEntity.status(201).body(createdFood);
    }

    @GetMapping("/{foodId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Food> getFoodById(@PathVariable int foodId) {
        Food food = foodService.getFoodById(foodId);
        return ResponseEntity.status(200).body(food);
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return ResponseEntity.status(200).body(foods);
    }

    @PutMapping("/{foodId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Food> updateFood(@PathVariable int foodId, @RequestBody Food foodDetails) {
        Food updatedFood = foodService.updateFood(foodId, foodDetails);
        return ResponseEntity.status(200).body(updatedFood);
    }

    @DeleteMapping("/{foodId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Boolean> deleteFood(@PathVariable int foodId) {
        boolean isDeleted = foodService.deleteFood(foodId);
        return ResponseEntity.status(200).body(isDeleted);
    }
}