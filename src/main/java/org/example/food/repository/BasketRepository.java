package org.example.food.repository;

import org.example.food.domain.Basket;
import org.example.food.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByMember(Member member);

    Optional<Basket> findByMemberId(Long memberId);
}