package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.InvalidInputException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Food;
import com.examly.springapp.repository.FoodRepo;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepo foodRepo;

    @Override
    public Food addFood(Food food) {
        if (food == null || food.getFoodName() == null || food.getFoodName().isEmpty()) {
            throw new InvalidInputException("Food name cannot be null or empty.");
        }
        if (food.getPrice() <= 0) {
            throw new InvalidInputException("Price must be greater than zero.");
        }
        if (food.getStockQuantity() < 0) {
            throw new InvalidInputException("Stock quantity cannot be negative.");
        }
        return foodRepo.save(food);
    }

    @Override
    public List<Food> getAllFoods() {
        List<Food> foods = foodRepo.findAll();
        if (foods.isEmpty()) {
            throw new ResourceNotFoundException("No foods available.");
        }
        return foods;
    }

    @Override
    public Optional<Food> getFoodById(int foodId) {
        if (foodId <= 0) {
            throw new InvalidInputException("Food ID must be positive.");
        }
        Optional<Food> food = foodRepo.findById(foodId);
        if (!food.isPresent()) {
            throw new ResourceNotFoundException("Food not found with ID: " + foodId);
        }
        return food;
    }

    @Override
    public Optional<Food> updateFood(int foodId, Food foodDetails) {
        if (foodId <= 0) {
            throw new InvalidInputException("Food ID must be positive.");
        }
        if (foodDetails == null || foodDetails.getFoodName() == null || foodDetails.getFoodName().isEmpty()) {
            throw new InvalidInputException("Food name cannot be null or empty.");
        }
        if (foodDetails.getPrice() <= 0) {
            throw new InvalidInputException("Price must be greater than zero.");
        }
        if (foodDetails.getStockQuantity() < 0) {
            throw new InvalidInputException("Stock quantity cannot be negative.");
        }

        Optional<Food> existingFood = foodRepo.findById(foodId);
        if (!existingFood.isPresent()) {
            throw new ResourceNotFoundException("Food not found with ID: " + foodId);
        }
        foodDetails.setFoodId(foodId);
        return Optional.of(foodRepo.save(foodDetails));
    }

    @Override
    public boolean deleteFood(int foodId) {
        if (foodId <= 0) {
            throw new InvalidInputException("Food ID must be positive.");
        }
        if (!foodRepo.existsById(foodId)) {
            throw new ResourceNotFoundException("Food not found with ID: " + foodId);
        }
        foodRepo.deleteById(foodId);
        return true;
    }
}