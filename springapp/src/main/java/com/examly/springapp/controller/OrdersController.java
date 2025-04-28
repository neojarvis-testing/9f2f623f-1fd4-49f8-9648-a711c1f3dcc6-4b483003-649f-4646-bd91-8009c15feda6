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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Endpoints for managing orders")

public class OrdersController {

    @Autowired
    OrderServiceImpl orderServiceImpl;

    @Operation(summary = "Add a new order", description = "Allows a user to add a new order.")
    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<Orders> addOrder(@RequestBody Orders orders) {
        Orders newOrder = orderServiceImpl.addOrder(orders);
        return ResponseEntity.status(201).body(newOrder);
    }

    @Operation(summary = "Get order by ID", description = "Allows an admin or user to retrieve an order by its ID.")
    @GetMapping("/{orderId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Optional<Orders>> getOrderById(@PathVariable int orderId) {
        Optional<Orders> order = orderServiceImpl.getOrderById(orderId);
        return ResponseEntity.status(200).body(order);
    }

    @Operation(summary = "Get all orders", description = "Allows an admin to retrieve all orders.")
    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = orderServiceImpl.getAllOrders();
        return ResponseEntity.status(200).body(orders);
    }

    @Operation(summary = "Update order by ID", description = "Allows an admin to update the details of an order by its ID.")
    @PutMapping("/{orderId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Optional<Orders>> updateOrder(@PathVariable int orderId, @RequestBody Orders orderDetails) {
        Optional<Orders> updatedOrder = orderServiceImpl.updateOrder(orderId, orderDetails);
        return ResponseEntity.status(200).body(updatedOrder);
    }

    @Operation(summary = "Delete order by ID", description = "Allows a user to delete an order by its ID.")
    @DeleteMapping("/{orderId}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable int orderId) {
        boolean isDeleted = orderServiceImpl.deleteOrder(orderId);
        return ResponseEntity.status(200).body(isDeleted);
    }

    @Operation(summary = "Get orders by user ID", description = "Allows a user to retrieve all orders they have placed.")
    @GetMapping("/user/{userId}")
    @RolesAllowed("USER")
    public ResponseEntity<Optional<List<Orders>>> getOrdersByUserId(@PathVariable int userId) {
        Optional<List<Orders>> orders = orderServiceImpl.getOrdersByUserId(userId);
        return ResponseEntity.status(200).body(orders);
    }
}