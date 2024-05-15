package org.example.food.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.*;
import org.example.food.dto.NotificationResponse;
import org.example.food.repository.MemberRepository;
import org.example.food.repository.NotificationRepository;
import org.example.food.repository.RestaurantRepository;
import org.example.food.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Notification sendNotification(Long memberId, String message, NotificationType notificationType) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        Notification notification = Notification.builder()
                .message(message)
                .member(member)
                .notificationType(notificationType)
                .build();

        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void notifyAdmins(String message, NotificationType notificationType, Restaurant restaurant) {
        List<Member> admins = memberRepository.findByRole(Role.ROLE_ADMIN);
        for (Member admin : admins) {
            Notification notification = sendNotification(admin.getId(), message, notificationType);
            notification.setRestaurant(restaurant);
        }
    }

    @Override
    public List<NotificationResponse> getAllNotifications(Long memberId) {
        List<Notification> notifications = notificationRepository.findByMemberId(memberId);
        return notifications.stream()
                .map(NotificationResponse::of)
                .filter(notificationResponse -> notificationResponse.getRestaurantResponse() == null || !notificationResponse.getRestaurantResponse().isApproved())
                .collect(Collectors.toList());
    }

    @Override
    public void markAllNotificationsAsRead(Long memberId) {
        List<Notification> unreadNotifications = notificationRepository.findByMemberIdAndReadFalse(memberId);
        for (Notification notification : unreadNotifications) {
            notification.markAsRead();
            notificationRepository.save(notification);
        }
    }

    @Override
    public boolean hasUnreadNotifications(Long memberId) {
        return notificationRepository.existsByMemberIdAndReadFalse(memberId);
    }

    @Override
    public void deleteById(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
