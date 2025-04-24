package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    
        public Orders addOrder(Orders orders) {
            return orderRepo.save(orders);
        }
    
        public Optional<Orders> getOrderById(int orderId) {
            return orderRepo.findById(orderId);
        }
    
        public List<Orders> getAllOrders() {
            return orderRepo.findAll();
        }
    
        public Optional<Orders> updateOrder(int orderId, Orders orderDetails) {
            Optional<Orders> existingOrder = orderRepo.findById(orderId);
            if (!existingOrder.isPresent()) {
                return Optional.empty();
            }
            orderDetails.setOrderId(orderId);
            orderDetails = orderRepo.save(orderDetails);
            return Optional.of(orderDetails);
        }
    
        public boolean deleteOrder(int orderId) {
            if (orderRepo.existsById(orderId)) {
                orderRepo.deleteById(orderId);
                return true;
            }
            return false;
        }
    
        public Optional<List<Orders>> getOrdersByUserId(int userId) {
            User existingUser = userRepo.findById(userId).orElse(null);
            if (existingUser == null) {
                return Optional.empty();
            }
            List<Orders> userOrders = orderRepo.findOrdersByUserId(userId);
            if (userOrders.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(userOrders);
        }
    }
    

