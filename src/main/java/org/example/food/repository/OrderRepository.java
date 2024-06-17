package org.example.food.repository;

import org.example.food.domain.Order;
import org.example.food.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);
}