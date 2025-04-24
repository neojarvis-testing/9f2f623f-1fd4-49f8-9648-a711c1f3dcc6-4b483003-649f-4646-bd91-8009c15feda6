package com.examly.springapp.dto;

import jakarta.validation.constraints.*;

public class FoodDTO {

    private int foodId; // Food item ID

    @NotBlank(message = "Food name cannot be blank")
    @Size(min = 2, max = 100, message = "Food name must be between 2 and 100 characters")
    private String foodName; // Name of the food item

    @Positive(message = "Price must be greater than 0")
    private double price; // Price of the food item

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQuantity; // Quantity of food stock

    private String photo; 

    private int userId;

    // Getters and Setters
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Default Constructor
    public FoodDTO() {}

    // Parameterized Constructor
    public FoodDTO(int foodId, String foodName, double price, int stockQuantity, String photo, int userId) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.photo = photo;
        this.userId = userId;
    }
}