package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Food;

public interface FoodService {
    public Food addFood(Food food);
    public List<Food> getAllFoods();
    public Optional<Food> getFoodById(int foodId);
    public Optional<Food> updateFood(int foodId, Food foodDetails);
    public boolean deleteFood(int foodId);

}
