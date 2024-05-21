package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.dto.NotificationResponse;
import org.example.food.security.PrincipalDetails;
import org.example.food.service.NotificationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification")
    public String notification(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Long memberId = principalDetails.getMember().getId();
        notificationService.markAllNotificationsAsRead(memberId);

        List<NotificationResponse> allNotifications = notificationService.findByMemberId(memberId);
        model.addAttribute("notifications", allNotifications);
        return "notification";
    }

    @PostMapping("/notification/delete/{id}")
    public String delete(@PathVariable Long id) {
        notificationService.deleteById(id);
        return "notification";
    }
}
