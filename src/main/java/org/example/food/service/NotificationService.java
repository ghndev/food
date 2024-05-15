package org.example.food.service;

import org.example.food.domain.Notification;
import org.example.food.domain.NotificationType;
import org.example.food.domain.Restaurant;
import org.example.food.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {
    Notification sendNotification(Long memberId, String message, NotificationType notificationType);

    void notifyAdmins(String message, NotificationType notificationType, Restaurant restaurant);

    List<NotificationResponse> getAllNotifications(Long memberId);

    void markAllNotificationsAsRead(Long memberId);

    boolean hasUnreadNotifications(Long memberId);

    void deleteById(Long notificationId);
}
