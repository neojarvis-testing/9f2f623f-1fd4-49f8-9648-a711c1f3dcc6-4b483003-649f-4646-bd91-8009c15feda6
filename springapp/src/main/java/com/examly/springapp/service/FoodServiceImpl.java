package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.examly.springapp.exception.InvalidInputException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Food;
import com.examly.springapp.repository.FoodRepo;

@Service
public class FoodServiceImpl implements FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);

    @Autowired
    private FoodRepo foodRepo;

    @Override
    public Food addFood(Food food) {
        logger.info("Adding food: {}", food);
        if (food == null || food.getFoodName() == null || food.getFoodName().isEmpty()) {
            logger.error("Invalid food name: {}", food);
            throw new InvalidInputException("Food name cannot be null or empty.");
        }
        if (food.getPrice() <= 0) {
            logger.error("Invalid food price: {}", food.getPrice());
            throw new InvalidInputException("Price must be greater than zero.");
        }
        if (food.getStockQuantity() < 0) {
            logger.error("Invalid stock quantity: {}", food.getStockQuantity());
            throw new InvalidInputException("Stock quantity cannot be negative.");
        }
        Food savedFood = foodRepo.save(food);
        logger.info("Food added successfully: {}", savedFood);
        return savedFood;
    }

    @Override
    public List<Food> getAllFoods() {
        logger.info("Fetching all foods");
        List<Food> foods = foodRepo.findAll();
        if (foods.isEmpty()) {
            logger.error("No foods available");
            throw new ResourceNotFoundException("No foods available.");
        }
        logger.info("Foods found: {}", foods);
        return foods;
    }

    @Override
    public Food getFoodById(int foodId) {
        logger.info("Fetching food by ID: {}", foodId);
        if (foodId <= 0) {
            logger.error("Invalid food ID: {}", foodId);
            throw new InvalidInputException("Food ID must be positive.");
        }
        Food food = foodRepo.findById(foodId).orElse(null);
        if (food == null) {
            logger.error("Food not found with ID: {}", foodId);
            throw new ResourceNotFoundException("Food not found with ID: " + foodId);
        }
        logger.info("Food found: {}", food);
        return food;
    }

    @Override
    public Food updateFood(int foodId, Food foodDetails) {
        logger.info("Updating food with ID: {}", foodId);
        if (foodId <= 0) {
            logger.error("Invalid food ID: {}", foodId);
            throw new InvalidInputException("Food ID must be positive.");
        }
        if (foodDetails == null || foodDetails.getFoodName() == null || foodDetails.getFoodName().isEmpty()) {
            logger.error("Invalid food name: {}", foodDetails);
            throw new InvalidInputException("Food name cannot be null or empty.");
        }
        if (foodDetails.getPrice() <= 0) {
            logger.error("Invalid food price: {}", foodDetails.getPrice());
            throw new InvalidInputException("Price must be greater than zero.");
        }
        if (foodDetails.getStockQuantity() < 0) {
            logger.error("Invalid stock quantity: {}", foodDetails.getStockQuantity());
            throw new InvalidInputException("Stock quantity cannot be negative.");
        }

        Food existingFood = foodRepo.findById(foodId).orElse(null);
        if (existingFood == null) {
            logger.error("Food not found with ID: {}", foodId);
            throw new ResourceNotFoundException("Food not found with ID: " + foodId);
        }
        foodDetails.setFoodId(foodId);
        Food updatedFood = foodRepo.save(foodDetails);
        logger.info("Food updated successfully: {}", updatedFood);
        return updatedFood;
    }

    @Override
    public boolean deleteFood(int foodId) {
        logger.info("Deleting food with ID: {}", foodId);
        if (foodId <= 0) {
            logger.error("Invalid food ID: {}", foodId);
            throw new InvalidInputException("Food ID must be positive.");
        }
        if (!foodRepo.existsById(foodId)) {
            logger.error("Food not found with ID: {}", foodId);
            throw new ResourceNotFoundException("Food not found with ID: " + foodId);
        }
        foodRepo.deleteById(foodId);
        logger.info("Food deleted successfully with ID: {}", foodId);
        return true;
    }
}
