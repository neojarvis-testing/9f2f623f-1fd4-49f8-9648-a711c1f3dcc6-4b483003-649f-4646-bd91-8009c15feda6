package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.InvalidInputException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Food;
import com.examly.springapp.repository.FoodRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FoodServiceImpl implements FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);

    @Autowired
    private FoodRepo foodRepo;

    @Override
    public Food addFood(Food food) {
        logger.info("Adding food: {}", food);
        if (food == null || food.getFoodName() == null || food.getFoodName().isEmpty()) {
            logger.error("Invalid input: Food name cannot be null or empty.");
            throw new InvalidInputException("Food name cannot be null or empty.");
        }
        if (food.getPrice() <= 0) {
            logger.error("Invalid input: Price must be greater than zero.");
            throw new InvalidInputException("Price must be greater than zero.");
        }
        if (food.getStockQuantity() < 0) {
            logger.error("Invalid input: Stock quantity cannot be negative.");
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
            logger.error("No foods available.");
            throw new ResourceNotFoundException("No foods available.");
        }
        logger.info("All foods fetched successfully");
        return foods;
    }

    @Override
    public Optional<Food> getFoodById(int foodId) {
        logger.info("Fetching food with ID: {}", foodId);
        if (foodId <= 0) {
            logger.error("Invalid input: Food ID must be positive.");
            throw new InvalidInputException("Food ID must be positive.");
        }
        Optional<Food> food = foodRepo.findById(foodId);
        if (!food.isPresent()) {
            logger.error("Food not found with ID: {}", foodId);
            throw new ResourceNotFoundException("Food not found with ID: " + foodId);
        }
        logger.info("Food fetched successfully: {}", food);
        return food;
    }

    @Override
    public Optional<Food> updateFood(int foodId, Food foodDetails) {
        logger.info("Updating food with ID: {}", foodId);
        if (foodId <= 0) {
            logger.error("Invalid input: Food ID must be positive.");
            throw new InvalidInputException("Food ID must be positive.");
        }
        if (foodDetails == null || foodDetails.getFoodName() == null || foodDetails.getFoodName().isEmpty()) {
            logger.error("Invalid input: Food name cannot be null or empty.");
            throw new InvalidInputException("Food name cannot be null or empty.");
        }
        if (foodDetails.getPrice() <= 0) {
            logger.error("Invalid input: Price must be greater than zero.");
            throw new InvalidInputException("Price must be greater than zero.");
        }
        if (foodDetails.getStockQuantity() < 0) {
            logger.error("Invalid input: Stock quantity cannot be negative.");
            throw new InvalidInputException("Stock quantity cannot be negative.");
        }

        Optional<Food> existingFood = foodRepo.findById(foodId);
        if (!existingFood.isPresent()) {
            logger.error("Food not found with ID: {}", foodId);
            throw new ResourceNotFoundException("Food not found with ID: " + foodId);
        }
        foodDetails.setFoodId(foodId);
        Food updatedFood = foodRepo.save(foodDetails);
        logger.info("Food updated successfully: {}", updatedFood);
        return Optional.of(updatedFood);
    }

    @Override
    public boolean deleteFood(int foodId) {
        logger.info("Deleting food with ID: {}", foodId);
        if (foodId <= 0) {
            logger.error("Invalid input: Food ID must be positive.");
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
