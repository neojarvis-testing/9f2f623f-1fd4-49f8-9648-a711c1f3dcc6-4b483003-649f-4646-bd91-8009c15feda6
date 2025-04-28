package com.examly.springapp.controller;

import java.util.List;

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
import com.examly.springapp.service.OrderService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    OrderService orderService;

    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<Orders> addOrder(@RequestBody Orders orders) {
        Orders newOrder = orderService.addOrder(orders);
        return ResponseEntity.status(201).body(newOrder);
    }

    @GetMapping("/{orderId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Orders> getOrderById(@PathVariable int orderId) {
        Orders order = orderService.getOrderById(orderId);
        return ResponseEntity.status(200).body(order);
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.status(200).body(orders);
    }

    @PutMapping("/{orderId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Orders> updateOrder(@PathVariable int orderId, @RequestBody Orders orderDetails) {
        Orders updatedOrder = orderService.updateOrder(orderId, orderDetails);
        return ResponseEntity.status(200).body(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable int orderId) {
        boolean isDeleted = orderService.deleteOrder(orderId);
        return ResponseEntity.status(200).body(isDeleted);
    }

    @GetMapping("/user/{userId}")
    @RolesAllowed("USER")
    public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable int userId) {
        List<Orders> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(200).body(orders);
    }
}