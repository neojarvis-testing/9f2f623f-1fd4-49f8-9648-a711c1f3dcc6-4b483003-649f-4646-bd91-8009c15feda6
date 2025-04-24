package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Orders;

public interface OrderService {
    public Orders addOrder(Orders orders);
    public Optional<Orders> getOrderById(int orderId);
    public List<Orders> getAllOrders();
    public Optional<Orders> updateOrder(int orderId, Orders orderDetails);
    public boolean deleteOrder(int orderId);
    public Optional<List<Orders>> getOrdersByUserId(int userId);

}
