package org.example.food.repository;

import org.example.food.domain.Member;
import org.example.food.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByMemberAndReadFalse(Member member); // 읽지 않은 알림 조회
    boolean existsByMemberIdAndReadFalse(Long member_id);
    List<Notification> findByMemberId(Long memberId);
    List<Notification> findByMemberIdAndReadFalse(Long memberId);
}
