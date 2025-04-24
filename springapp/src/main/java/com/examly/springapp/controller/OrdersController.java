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
        Orders newOrder = orderServiceImpl.addOrder(orders);
        return ResponseEntity.status(201).body(newOrder);
    }

    @GetMapping("/{orderId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Optional<Orders>> getOrderById(@PathVariable int orderId) {
        Optional<Orders> order = orderServiceImpl.getOrderById(orderId);
        return ResponseEntity.status(200).body(order);
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = orderServiceImpl.getAllOrders();
        return ResponseEntity.status(200).body(orders);
    }

    @PutMapping("/{orderId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Optional<Orders>> updateOrder(@PathVariable int orderId, @RequestBody Orders orderDetails) {
        Optional<Orders> updatedOrder = orderServiceImpl.updateOrder(orderId, orderDetails);
        return ResponseEntity.status(200).body(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable int orderId) {
        boolean isDeleted = orderServiceImpl.deleteOrder(orderId);
        return ResponseEntity.status(200).body(isDeleted);
    }

    @GetMapping("/user/{userId}")
    @RolesAllowed("USER")
    public ResponseEntity<Optional<List<Orders>>> getOrdersByUserId(@PathVariable int userId) {
        Optional<List<Orders>> orders = orderServiceImpl.getOrdersByUserId(userId);
        return ResponseEntity.status(200).body(orders);
    }
}