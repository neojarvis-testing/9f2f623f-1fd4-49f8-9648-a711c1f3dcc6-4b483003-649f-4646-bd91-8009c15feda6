package com.examly.springapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.InvalidInputException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.Food;
import com.examly.springapp.model.Orders;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.FoodRepo;
import com.examly.springapp.repository.OrderRepo;
import com.examly.springapp.repository.UserRepo;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    FoodRepo foodRepo; 

    @Override
    public Orders addOrder(Orders orders) {
    if (orders == null || orders.getOrderStatus() == null || orders.getOrderStatus().isEmpty()) {
        throw new InvalidInputException("Order status cannot be null or empty.");
    }
    // if (orders.getTotalAmount() <= 0) {
    //     throw new InvalidInputException("Total amount must be greater than zero.");
    // }
    if (orders.getQuantity() <= 0) {
        throw new InvalidInputException("Quantity must be greater than zero.");
    }
    if (orders.getOrderDate() == null) {
        throw new InvalidInputException("Order date cannot be null.");
    }

    // Fetch and set User 
    User user = userRepo.findById(orders.getUser().getUserId()).orElse(null);
    if (user == null) {
        throw new UserNotFoundException("User not found");
    }
    orders.setUser(user);

    // Fetch and set Food
    Food food = foodRepo.findById(orders.getFood().getFoodId()).orElse(null);
    if (food == null) {
        throw new ResourceNotFoundException("Food not found"); 
    }
    if(food.getStockQuantity()<orders.getQuantity()){
        if(food.getStockQuantity()==0){
            throw new ResourceNotFoundException(food.getFoodName()+" Out of Stock!!");
        }
        throw new ResourceNotFoundException("Available Quantity of "+food.getFoodName()+" is "+food.getStockQuantity());
    }
    orders.setFood(food);

    // Set default Order Status
    orders.setOrderStatus("Pending");
    orders.setOrderDate(LocalDate.now());
    // orders.setTotalAmount(food.getPrice()*orders.getQuantity());
    food.setStockQuantity(food.getStockQuantity()-orders.getQuantity());
    return orderRepo.save(orders);
}

    @Override
    public Orders getOrderById(int orderId) {
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be positive.");
        }
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order==null) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        return order;
    }

    @Override
    public List<Orders> getAllOrders() {
        List<Orders> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders available.");
        }
        return orders;
    }

    @Override
    public Orders updateOrder(int orderId, Orders orderDetails) {
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be positive.");
        }
        Orders existingOrder = orderRepo.findById(orderId).orElse(null);
        if (existingOrder==null) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        if (orderDetails == null || orderDetails.getOrderStatus() == null || orderDetails.getOrderStatus().isEmpty()) {
            throw new InvalidInputException("Order status cannot be null or empty.");
        }
        // if (orderDetails.getTotalAmount() <= 0) {
        //     throw new InvalidInputException("Total amount must be greater than zero.");
        // }
        if (orderDetails.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (orderDetails.getOrderDate() == null) {
            throw new InvalidInputException("Order date cannot be null.");
        }

        Food food = foodRepo.findById(orderDetails.getFood().getFoodId()).orElse(null);
        if (food == null) {
            throw new ResourceNotFoundException("Food not found"); 
        }
        if(food.getStockQuantity()<orderDetails.getQuantity()){
            if(food.getStockQuantity()==0){
                throw new ResourceNotFoundException(food.getFoodName()+" Out of Stock!!");
            }
            throw new ResourceNotFoundException("Available Quantity of "+food.getFoodName()+" is "+food.getStockQuantity());
        }
        orderDetails.setOrderId(orderId);
        orderDetails.setOrderStatus(existingOrder.getOrderStatus());
        // orderDetails.setOrderDate(existingOrder.getOrderDate());
        // orderDetails.setTotalAmount(food.getPrice()*orderDetails.getQuantity());
        // food.setStockQuantity(food.getStockQuantity()-orderDetails.getQuantity());
        return orderRepo.save(orderDetails);
    }

    @Override
    public boolean deleteOrder(int orderId) {
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be positive.");
        }
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order==null) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepo.deleteById(orderId);
        return true;
    }

    @Override
    public List<Orders> getOrdersByUserId(int userId) {
        if (userId <= 0) {
            throw new InvalidInputException("User ID must be positive.");
        }
        User existingUser = userRepo.findById(userId).orElse(null);
        if (existingUser==null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        List<Orders> userOrders = orderRepo.findOrdersByUserId(userId);
        if (userOrders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for User ID: " + userId);
        }
        return userOrders;
    }
}