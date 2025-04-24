package com.examly.springapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Food;
import com.examly.springapp.service.FoodServiceImpl;

import jakarta.annotation.security.RolesAllowed;

@RestController
public class FoodController {
    @Autowired 
    FoodServiceImpl foodService;

    @PostMapping("api/food")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Food> addFood(@RequestBody Food food) {
        Food createdFood = foodService.addFood(food);
        return ResponseEntity.status(201).body(createdFood);
    }

    // View Food by Id
    @GetMapping("/{foodId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> getFoodById(@PathVariable int foodId) {
        Optional<Food> food = foodService.getFoodById(foodId);
        if (food.isPresent()) {
            return ResponseEntity.status(200).body(food.get());
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // View All Foods 
    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<?> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        if (foods.isEmpty()) {
            return ResponseEntity.status(204).build(); // No Content
        }
        return ResponseEntity.status(200).body(foods);
    }

    // Edit Food
    @PutMapping("/{foodId}")
    @RolesAllowed("ADMIN") 
    public ResponseEntity<?> updateFood(@PathVariable int foodId, @RequestBody Food foodDetails) {
        Optional<Food> updatedFood = foodService.updateFood(foodId, foodDetails);
        if (updatedFood.isPresent()) {
            return ResponseEntity.status(200).body(updatedFood.get());
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // Delete Food
    @DeleteMapping("/{foodId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<?> deleteFood(@PathVariable int foodId) {
        boolean isDeleted = foodService.deleteFood(foodId);
        if (isDeleted) {
            return ResponseEntity.status(204).build(); // No Content
        } else {
            return ResponseEntity.status(404).build();
        }
    }




    
}
