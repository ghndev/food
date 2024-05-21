package org.example.food.repository;

import org.example.food.domain.Member;
import org.example.food.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByApproved(boolean approved);
    List<Restaurant> findByOwnerAndApproved(Member owner, boolean approved);

    List<Restaurant> findByOwnerIdAndApprovedTrue(Long ownerId);
}
