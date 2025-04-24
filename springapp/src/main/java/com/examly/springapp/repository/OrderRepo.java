package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Orders;

public interface OrderRepo extends JpaRepository<Orders,Integer> {

}
