package com.examly.springapp.utility;

import com.examly.springapp.dto.FoodDTO;
import com.examly.springapp.model.Food;
import com.examly.springapp.model.User;

public class FoodMapper {

    // Converts FoodDTO to Food entity
    public static Food mapToEntity(FoodDTO foodDTO, User user) {
        Food food = new Food();
        food.setFoodId(foodDTO.getFoodId());
        food.setFoodName(foodDTO.getFoodName());
        food.setPrice(foodDTO.getPrice());
        food.setStockQuantity(foodDTO.getStockQuantity());
        food.setPhoto(foodDTO.getPhoto());
        food.setUser(user);
        return food;
    }

    // Converts Food entity to FoodDTO
    public static FoodDTO mapToDTO(Food food) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setFoodId(food.getFoodId());
        foodDTO.setFoodName(food.getFoodName());
        foodDTO.setPrice(food.getPrice());
        foodDTO.setStockQuantity(food.getStockQuantity());
        foodDTO.setPhoto(food.getPhoto());
        return foodDTO;
    }
}