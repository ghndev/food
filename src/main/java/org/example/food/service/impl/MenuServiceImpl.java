package org.example.food.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Menu;
import org.example.food.domain.MenuType;
import org.example.food.domain.Restaurant;
import org.example.food.dto.MenuForm;
import org.example.food.dto.MenuResponse;
import org.example.food.repository.MenuRepository;
import org.example.food.repository.RestaurantRepository;
import org.example.food.service.MenuService;
import org.example.food.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
    public void save(MenuForm menuForm, Long restaurantId) throws IOException {
        Menu menu = menuForm.toEntity();

        MultipartFile image = menuForm.getImage();
        String imagePath = s3Service.upload(image, "images");
        menu.updateImage(imagePath);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        menu.setRestaurant(restaurant);

        menuRepository.save(menu);
    }

    @Override
    public List<MenuResponse> findMenusByRestaurantId(Long restaurantId) {
        List<Menu> menus = menuRepository.findByRestaurantId(restaurantId);
        return menus.stream()
                .map(MenuResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuResponse> findMenusByType(MenuType type) {
        List<Menu> menus = menuRepository.findByType(type);
        return menus.stream()
                .map(MenuResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public MenuResponse findById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

        return MenuResponse.of(menu);
    }
}
