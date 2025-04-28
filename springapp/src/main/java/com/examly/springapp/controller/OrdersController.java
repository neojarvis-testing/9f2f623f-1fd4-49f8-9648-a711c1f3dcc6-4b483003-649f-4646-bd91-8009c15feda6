package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Orders;
import com.examly.springapp.service.OrderService;

import jakarta.annotation.security.RolesAllowed;

/**
 * This class is a REST controller for handling order-related requests.
 * It provides endpoints for creating, retrieving, updating, and deleting orders.
 */
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrderService orderService;

    /**
     * Constructor-based dependency injection for OrderService.
     */
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint for adding a new order.
     * Creates a new order and returns the created order with HTTP status 201.
     */
    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<Orders> addOrder(@RequestBody Orders orders) {
        Orders newOrder = orderService.addOrder(orders);
        return ResponseEntity.status(201).body(newOrder);
    }

    /**
     * Endpoint for retrieving an order by ID.
     * Retrieves an order by its ID and returns it with HTTP status 200.
     */
    @GetMapping("/{orderId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Orders> getOrderById(@PathVariable int orderId) {
        Orders order = orderService.getOrderById(orderId);
        return ResponseEntity.status(200).body(order);
    }

    /**
     * Endpoint for retrieving all orders.
     * Retrieves all orders and returns them with HTTP status 200.
     */
    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.status(200).body(orders);
    }

    /**
     * Endpoint for updating an order by ID.
     * Updates an order by its ID and returns the updated order with HTTP status 200.
     */
    @PutMapping("/{orderId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Orders> updateOrder(@PathVariable int orderId, @RequestBody Orders orderDetails) {
        Orders updatedOrder = orderService.updateOrder(orderId, orderDetails);
        return ResponseEntity.status(200).body(updatedOrder);
    }

    /**
     * Endpoint for deleting an order by ID.
     * Deletes an order by its ID and returns a boolean indicating the deletion status with HTTP status 200.
     */
    @DeleteMapping("/{orderId}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable int orderId) {
        boolean isDeleted = orderService.deleteOrder(orderId);
        return ResponseEntity.status(200).body(isDeleted);
    }

    /**
     * Endpoint for retrieving orders by user ID.
     * Retrieves orders by user ID and returns them with HTTP status 200.
     */
    @GetMapping("/user/{userId}")
    @RolesAllowed("USER")
    public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable int userId) {
        List<Orders> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(200).body(orders);
    }
}
