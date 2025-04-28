package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Food;
import com.examly.springapp.service.FoodService;
import com.examly.springapp.service.FoodServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/food")
@Tag(name = "Food", description = "Endpoints for managing food items")
public class FoodController {

    @Autowired
    FoodService foodService;
    
    @Operation(summary = "Add a new food item", description = "Allows an admin to add a new food item to the inventory.")
    @PostMapping
    //@RolesAllowed("ADMIN")
    public ResponseEntity<Food> addFood(@RequestBody Food food) {
        Food createdFood = foodService.addFood(food);
        return ResponseEntity.status(201).body(createdFood);
    }

    @Operation(summary = "Get food item by ID", description = "Allows an admin or user to retrieve a food item by its ID.")
    @GetMapping("/{foodId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Optional<Food>> getFoodById(@PathVariable int foodId) {
        Optional<Food> food = foodService.getFoodById(foodId);
        return ResponseEntity.status(200).body(food);
    }

    @Operation(summary = "Get all food items", description = "Allows an admin to retrieve all food items in the inventory.")
    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return ResponseEntity.status(200).body(foods);
    }
    @Operation(summary = "Update food item by ID", description = "Allows an admin to update the details of a food item by its ID.")
    @PutMapping("/{foodId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Optional<Food>> updateFood(@PathVariable int foodId, @RequestBody Food foodDetails) {
        Optional<Food> updatedFood = foodService.updateFood(foodId, foodDetails);
        return ResponseEntity.status(200).body(updatedFood);
    }

    @Operation(summary = "Delete food item by ID", description = "Allows an admin to delete a food item by its ID.")
    @DeleteMapping("/{foodId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Boolean> deleteFood(@PathVariable int foodId) {
        boolean isDeleted = foodService.deleteFood(foodId);
        return ResponseEntity.status(200).body(isDeleted);
    }
}

