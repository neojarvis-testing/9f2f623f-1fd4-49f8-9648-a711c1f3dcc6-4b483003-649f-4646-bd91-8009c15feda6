package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Food;
import com.examly.springapp.service.FoodService;

import jakarta.annotation.security.RolesAllowed;

/**
 * This class is a REST controller for handling food-related requests.
 * It provides endpoints for creating, retrieving, updating, and deleting food items.
 */
@RestController
@RequestMapping("api/food")
public class FoodController {

    private final FoodService foodService;

    /**
     * Constructor-based dependency injection for FoodService.
     */
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * Endpoint for adding a new food item.
     * Creates a new food item and returns the created food item with HTTP status 201.
     */
    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Food> addFood(@RequestBody Food food) {
        Food createdFood = foodService.addFood(food);
        return ResponseEntity.status(201).body(createdFood);
    }

    /**
     * Endpoint for retrieving a food item by ID.
     * Retrieves a food item by its ID and returns it with HTTP status 200.
     */
    @GetMapping("/{foodId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Food> getFoodById(@PathVariable int foodId) {
        Food food = foodService.getFoodById(foodId);
        return ResponseEntity.status(200).body(food);
    }

    /**
     * Endpoint for retrieving all food items.
     * Retrieves all food items and returns them with HTTP status 200.
     */
    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return ResponseEntity.status(200).body(foods);
    }

    /**
     * Endpoint for updating a food item by ID.
     * Updates a food item by its ID and returns the updated food item with HTTP status 200.
     */
    @PutMapping("/{foodId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Food> updateFood(@PathVariable int foodId, @RequestBody Food foodDetails) {
        Food updatedFood = foodService.updateFood(foodId, foodDetails);
        return ResponseEntity.status(200).body(updatedFood);
    }

    /**
     * Endpoint for deleting a food item by ID.
     * Deletes a food item by its ID and returns a boolean indicating the deletion status with HTTP status 200.
     */
    @DeleteMapping("/{foodId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Boolean> deleteFood(@PathVariable int foodId) {
        boolean isDeleted = foodService.deleteFood(foodId);
        return ResponseEntity.status(200).body(isDeleted);
    }
}
