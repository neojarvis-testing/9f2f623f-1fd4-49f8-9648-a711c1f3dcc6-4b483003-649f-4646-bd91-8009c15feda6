package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.examly.springapp.model.Orders;
import com.examly.springapp.service.OrderServiceImpl;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    OrderServiceImpl orderServiceImpl;

    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<Orders> addOrder(@RequestBody Orders orders) {
        orders = orderServiceImpl.addOrder(orders);
        return ResponseEntity.status(201).body(orders);
    }

    @GetMapping("/{orderId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> getOrderById(@PathVariable int orderId) {
        Optional<Orders> order = orderServiceImpl.getOrderById(orderId);
        if (order.isPresent()) {
            return ResponseEntity.status(200).body(order.get());
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<?> getAllOrders() {
        List<Orders> orders = orderServiceImpl.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build(); // No Content
        }
        return ResponseEntity.status(200).body(orders);
    }

    @PutMapping("/{orderId}")
    @RolesAllowed("ADMIN") 
    public ResponseEntity<?> updateOrder(@PathVariable int orderId, @RequestBody Orders orderDetails) {
        Optional<Orders> updatedOrder = orderServiceImpl.updateOrder(orderId, orderDetails);
        if (updatedOrder.isPresent()) {
            return ResponseEntity.status(200).body(updatedOrder.get());
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{orderId}")
    @RolesAllowed("USER")
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId) {
        boolean isDeleted = orderServiceImpl.deleteOrder(orderId);
        if (isDeleted) {
            return ResponseEntity.status(204).build(); // No Content
        } else {
            return ResponseEntity.status(404).build();
        }
    }   
    
    @GetMapping("/user/{userId}")
    @RolesAllowed({"USER"})
    public ResponseEntity<?> getOrdersByUserId(@PathVariable int userId) {
        Optional<List<Orders>> orders = orderServiceImpl.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build(); // No Content
        }
        return ResponseEntity.status(200).body(orders.get());
    }
}
