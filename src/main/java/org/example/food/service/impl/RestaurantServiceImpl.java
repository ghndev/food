package org.example.food.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Member;
import org.example.food.domain.NotificationType;
import org.example.food.domain.Restaurant;
import org.example.food.domain.Role;
import org.example.food.dto.RestaurantForm;
import org.example.food.repository.RestaurantRepository;
import org.example.food.security.PrincipalDetails;
import org.example.food.service.NotificationService;
import org.example.food.service.RestaurantService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final NotificationService notificationService;

    @Override
    public void save(RestaurantForm restaurantForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        Restaurant restaurant = restaurantForm.toEntity(principalDetails.getMember());
        restaurantRepository.save(restaurant);

        notificationService.notifyAdmins("입점 신청", NotificationType.REGISTER, restaurant);
    }

    @Transactional
    public void approve(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        Member owner = restaurant.getOwner();
        owner.updateRole(Role.ROLE_OWNER);

        restaurant.setApproved(true);
    }
}
