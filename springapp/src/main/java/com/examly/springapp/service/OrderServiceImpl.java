package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            logger.error("Invalid input: Order status cannot be null or empty.");
            throw new InvalidInputException("Order status cannot be null or empty.");
        }
        if (orders.getTotalAmount() <= 0) {
            logger.error("Invalid input: Total amount must be greater than zero.");
            throw new InvalidInputException("Total amount must be greater than zero.");
        }
        if (orders.getQuantity() <= 0) {
            logger.error("Invalid input: Quantity must be greater than zero.");
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (orders.getOrderDate() == null) {
            logger.error("Invalid input: Order date cannot be null.");
            throw new InvalidInputException("Order date cannot be null.");
        }

        // Fetch and set User 
        User user = userRepo.findById(orders.getUser().getUserId()).orElse(null);
        if (user == null) {
            logger.error("User not found with ID: {}", orders.getUser().getUserId());
            throw new UserNotFoundException("User not found");
        }
        orders.setUser(user);

        // Fetch and set Food
        Food food = foodRepo.findById(orders.getFood().getFoodId()).orElse(null);
        if (food == null) {
            logger.error("Food not found with ID: {}", orders.getFood().getFoodId());
            throw new ResourceNotFoundException("Food not found"); 
        }
        orders.setFood(food);

        // Set default Order Status
        orders.setOrderStatus("Pending");

        Orders savedOrder = orderRepo.save(orders);
        logger.info("Order added successfully: {}", savedOrder);
        return savedOrder;
    }

    @Override
    public Optional<Orders> getOrderById(int orderId) {
        logger.info("Fetching order with ID: {}", orderId);
        if (orderId <= 0) {
            logger.error("Invalid input: Order ID must be positive.");
            throw new InvalidInputException("Order ID must be positive.");
        }
        Optional<Orders> order = orderRepo.findById(orderId);
        if (!order.isPresent()) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        logger.info("Order fetched successfully: {}", order);
        return order;
    }

    @Override
    public List<Orders> getAllOrders() {
        logger.info("Fetching all orders");
        List<Orders> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            logger.error("No orders available.");
            throw new ResourceNotFoundException("No orders available.");
        }
        logger.info("All orders fetched successfully");
        return orders;
    }

    @Override
    public Optional<Orders> updateOrder(int orderId, Orders orderDetails) {
        logger.info("Updating order with ID: {}", orderId);
        if (orderId <= 0) {
            logger.error("Invalid input: Order ID must be positive.");
            throw new InvalidInputException("Order ID must be positive.");
        }
        Optional<Orders> existingOrder = orderRepo.findById(orderId);
        if (!existingOrder.isPresent()) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        if (orderDetails == null || orderDetails.getOrderStatus() == null || orderDetails.getOrderStatus().isEmpty()) {
            logger.error("Invalid input: Order status cannot be null or empty.");
            throw new InvalidInputException("Order status cannot be null or empty.");
        }
        if (orderDetails.getTotalAmount() <= 0) {
            logger.error("Invalid input: Total amount must be greater than zero.");
            throw new InvalidInputException("Total amount must be greater than zero.");
        }
        if (orderDetails.getQuantity() <= 0) {
            logger.error("Invalid input: Quantity must be greater than zero.");
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (orderDetails.getOrderDate() == null) {
            logger.error("Invalid input: Order date cannot be null.");
            throw new InvalidInputException("Order date cannot be null.");
        }
        orderDetails.setOrderId(orderId);
        Orders updatedOrder = orderRepo.save(orderDetails);
        logger.info("Order updated successfully: {}", updatedOrder);
        return Optional.of(updatedOrder);
    }

    @Override
    public boolean deleteOrder(int orderId) {
        logger.info("Deleting order with ID: {}", orderId);
        if (orderId <= 0) {
            logger.error("Invalid input: Order ID must be positive.");
            throw new InvalidInputException("Order ID must be positive.");
        }
        Optional<Orders> order = orderRepo.findById(orderId);
        if (!order.isPresent()) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepo.deleteById(orderId);
        logger.info("Order deleted successfully with ID: {}", orderId);
        return true;
    }

    @Override
    public Optional<List<Orders>> getOrdersByUserId(int userId) {
        logger.info("Fetching orders for User ID: {}", userId);
        if (userId <= 0) {
            logger.error("Invalid input: User ID must be positive.");
            throw new InvalidInputException("User ID must be positive.");
        }
        Optional<User> existingUser = userRepo.findById(userId);
        if (!existingUser.isPresent()) {
            logger.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        List<Orders> userOrders = orderRepo.findOrdersByUserId(userId);
        if (userOrders.isEmpty()) {
            logger.error("No orders found for User ID: {}", userId);
            throw new ResourceNotFoundException("No orders found for User ID: " + userId);
        }
        logger.info("Orders fetched successfully for User ID: {}", userId);
        return Optional.of(userOrders);
    }
}
