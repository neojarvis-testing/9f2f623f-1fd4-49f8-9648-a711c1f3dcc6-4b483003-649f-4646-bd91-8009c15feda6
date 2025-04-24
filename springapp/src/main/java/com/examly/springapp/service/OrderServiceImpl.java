package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.InvalidInputException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Orders;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.OrderRepo;
import com.examly.springapp.repository.UserRepo;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Orders addOrder(Orders orders) {
        if (orders == null || orders.getOrderStatus() == null || orders.getOrderStatus().isEmpty()) {
            throw new InvalidInputException("Order status cannot be null or empty.");
        }
        if (orders.getTotalAmount() <= 0) {
            throw new InvalidInputException("Total amount must be greater than zero.");
        }
        if (orders.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (orders.getOrderDate() == null) {
            throw new InvalidInputException("Order date cannot be null.");
        }
        return orderRepo.save(orders);
    }

    @Override
    public Optional<Orders> getOrderById(int orderId) {
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be positive.");
        }
        Optional<Orders> order = orderRepo.findById(orderId);
        if (!order.isPresent()) {
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
    public Optional<Orders> updateOrder(int orderId, Orders orderDetails) {
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be positive.");
        }
        Optional<Orders> existingOrder = orderRepo.findById(orderId);
        if (!existingOrder.isPresent()) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        if (orderDetails == null || orderDetails.getOrderStatus() == null || orderDetails.getOrderStatus().isEmpty()) {
            throw new InvalidInputException("Order status cannot be null or empty.");
        }
        if (orderDetails.getTotalAmount() <= 0) {
            throw new InvalidInputException("Total amount must be greater than zero.");
        }
        if (orderDetails.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (orderDetails.getOrderDate() == null) {
            throw new InvalidInputException("Order date cannot be null.");
        }
        orderDetails.setOrderId(orderId);
        return Optional.of(orderRepo.save(orderDetails));
    }

    @Override
    public boolean deleteOrder(int orderId) {
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be positive.");
        }
        Optional<Orders> order = orderRepo.findById(orderId);
        if (!order.isPresent()) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepo.deleteById(orderId);
        return true;
    }

    @Override
    public Optional<List<Orders>> getOrdersByUserId(int userId) {
        if (userId <= 0) {
            throw new InvalidInputException("User ID must be positive.");
        }
        Optional<User> existingUser = userRepo.findById(userId);
        if (!existingUser.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        List<Orders> userOrders = orderRepo.findOrdersByUserId(userId);
        if (userOrders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for User ID: " + userId);
        }
        return Optional.of(userOrders);
    }
}