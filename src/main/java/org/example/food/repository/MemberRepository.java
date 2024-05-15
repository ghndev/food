package org.example.food.repository;

import org.example.food.domain.Member;
import org.example.food.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByName(String name);
    List<Member> findByRole(Role role);
}
