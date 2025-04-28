package com.examly.springapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    FoodRepo foodRepo; 

    @Override
    public Orders addOrder(Orders orders) {
        logger.info("Adding order: {}", orders);
        if (orders == null || orders.getOrderStatus() == null || orders.getOrderStatus().isEmpty()) {
            logger.error("Invalid order status: {}", orders);
            throw new InvalidInputException("Order status cannot be null or empty.");
        }
        if (orders.getQuantity() <= 0) {
            logger.error("Invalid order quantity: {}", orders.getQuantity());
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (orders.getOrderDate() == null) {
            logger.error("Invalid order date: {}", orders.getOrderDate());
            throw new InvalidInputException("Order date cannot be null.");
        }

        // Fetch and set User 
        User user = userRepo.findById(orders.getUser().getUserId()).orElse(null);
        if (user == null) {
            logger.error("User not found: {}", orders.getUser().getUserId());
            throw new UserNotFoundException("User not found");
        }
        orders.setUser(user);

        // Fetch and set Food
        Food food = foodRepo.findById(orders.getFood().getFoodId()).orElse(null);
        if (food == null) {
            logger.error("Food not found: {}", orders.getFood().getFoodId());
            throw new ResourceNotFoundException("Food not found"); 
        }
        if (food.getStockQuantity() < orders.getQuantity()) {
            if (food.getStockQuantity() == 0) {
                logger.error("Food out of stock: {}", food.getFoodName());
                throw new ResourceNotFoundException(food.getFoodName() + " Out of Stock!!");
            }
            logger.error("Insufficient stock for {}: Available Quantity is {}", food.getFoodName(), food.getStockQuantity());
            throw new ResourceNotFoundException("Available Quantity of " + food.getFoodName() + " is " + food.getStockQuantity());
        }
        orders.setFood(food);

        // Set default Order Status
        orders.setOrderStatus("Pending");
        orders.setOrderDate(LocalDate.now());
        // orders.setTotalAmount(food.getPrice()*orders.getQuantity());
        food.setStockQuantity(food.getStockQuantity() - orders.getQuantity());
        Orders savedOrder = orderRepo.save(orders);
        logger.info("Order added successfully: {}", savedOrder);
        return savedOrder;
    }

    @Override
    public Orders getOrderById(int orderId) {
        logger.info("Fetching order by ID: {}", orderId);
        if (orderId <= 0) {
            logger.error("Invalid order ID: {}", orderId);
            throw new InvalidInputException("Order ID must be positive.");
        }
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order == null) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        logger.info("Order found: {}", order);
        return order;
    }

    @Override
    public List<Orders> getAllOrders() {
        logger.info("Fetching all orders");
        List<Orders> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            logger.error("No orders available");
            throw new ResourceNotFoundException("No orders available.");
        }
        logger.info("Orders found: {}", orders);
        return orders;
    }

    @Override
    public Orders updateOrder(int orderId, Orders orderDetails) {
        logger.info("Updating order with ID: {}", orderId);
        if (orderId <= 0) {
            logger.error("Invalid order ID: {}", orderId);
            throw new InvalidInputException("Order ID must be positive.");
        }
        Orders existingOrder = orderRepo.findById(orderId).orElse(null);
        if (existingOrder == null) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        if (orderDetails == null || orderDetails.getOrderStatus() == null || orderDetails.getOrderStatus().isEmpty()) {
            logger.error("Invalid order status: {}", orderDetails);
            throw new InvalidInputException("Order status cannot be null or empty.");
        }
        if (orderDetails.getQuantity() <= 0) {
            logger.error("Invalid order quantity: {}", orderDetails.getQuantity());
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (orderDetails.getOrderDate() == null) {
            logger.error("Invalid order date: {}", orderDetails.getOrderDate());
            throw new InvalidInputException("Order date cannot be null.");
        }

        Food food = foodRepo.findById(orderDetails.getFood().getFoodId()).orElse(null);
        if (food == null) {
            logger.error("Food not found: {}", orderDetails.getFood().getFoodId());
            throw new ResourceNotFoundException("Food not found"); 
        }
        if (food.getStockQuantity() < orderDetails.getQuantity()) {
            if (food.getStockQuantity() == 0) {
                logger.error("Food out of stock: {}", food.getFoodName());
                throw new ResourceNotFoundException(food.getFoodName() + " Out of Stock!!");
            }
            logger.error("Insufficient stock for {}: Available Quantity is {}", food.getFoodName(), food.getStockQuantity());
            throw new ResourceNotFoundException("Available Quantity of " + food.getFoodName() + " is " + food.getStockQuantity());
        }
        orderDetails.setOrderId(orderId);
        // orderDetails.setOrderStatus(existingOrder.getOrderStatus());
        // orderDetails.setOrderDate(existingOrder.getOrderDate());
        // orderDetails.setTotalAmount(food.getPrice()*orderDetails.getQuantity());
        // food.setStockQuantity(food.getStockQuantity()-orderDetails.getQuantity());
        Orders updatedOrder = orderRepo.save(orderDetails);
        logger.info("Order updated successfully: {}", updatedOrder);
        return updatedOrder;
    }

    @Override
    public boolean deleteOrder(int orderId) {
        logger.info("Deleting order with ID: {}", orderId);
        if (orderId <= 0) {
            logger.error("Invalid order ID: {}", orderId);
            throw new InvalidInputException("Order ID must be positive.");
        }
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order == null) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepo.deleteById(orderId);
        logger.info("Order deleted successfully with ID: {}", orderId);
        return true;
    }

    @Override
    public List<Orders> getOrdersByUserId(int userId) {
        logger.info("Fetching orders by User ID: {}", userId);
        if (userId <= 0) {
            logger.error("Invalid user ID: {}", userId);
            throw new InvalidInputException("User ID must be positive.");
        }
        User existingUser = userRepo.findById(userId).orElse(null);
        if (existingUser == null) {
            logger.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        List<Orders> userOrders = orderRepo.findOrdersByUserId(userId);
        if (userOrders.isEmpty()) {
            logger.error("No orders found for User ID: {}", userId);
            throw new ResourceNotFoundException("No orders found for User ID: " + userId);
        }
        logger.info("Orders found for User ID {}: {}", userId, userOrders);
        return userOrders;
    }
}
