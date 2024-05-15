package org.example.food.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.food.domain.Notification;
import org.example.food.domain.NotificationType;

@Getter
@Builder
public class NotificationResponse {
    private Long id;
    private String message;
    private boolean read;
    private NotificationType notificationType;
    private RestaurantResponse restaurantResponse;

    public static NotificationResponse of(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .read(notification.isRead())
                .notificationType(notification.getNotificationType())
                .restaurantResponse(RestaurantResponse.of(notification.getRestaurant()))
                .build();
    }
}
