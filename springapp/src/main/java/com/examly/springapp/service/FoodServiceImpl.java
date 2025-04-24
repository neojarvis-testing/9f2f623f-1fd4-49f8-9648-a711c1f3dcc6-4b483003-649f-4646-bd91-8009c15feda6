package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Food;
import com.examly.springapp.repository.FoodRepo;

@Service
public class FoodServiceImpl implements FoodService{
    @Autowired
    private FoodRepo foodRepo;

    public Food addFood(Food food) {
        return foodRepo.save(food);
    }
    public List<Food> getAllFoods() {
        return foodRepo.findAll();
    }

    public Optional<Food> getFoodById(int foodId) {
        return foodRepo.findById(foodId);

    }
    public Optional<Food> updateFood(int foodId, Food foodDetails) {
        Optional<Food> existingFood = foodRepo.findById(foodId);
        if(!existingFood.isPresent())
            return Optional.of(null);
        foodDetails.setFoodId(foodId);
        foodDetails = foodRepo.save(foodDetails);
        return Optional.of(foodDetails);
    }

    public boolean deleteFood(int foodId) {
        if (foodRepo.existsById(foodId)) {
            foodRepo.deleteById(foodId);
            return true;
        }
        return false;

  }




    
}
 