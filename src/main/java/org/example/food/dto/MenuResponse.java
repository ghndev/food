package org.example.food.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.food.domain.Menu;
import org.example.food.domain.MenuType;

@Getter
@Builder
public class MenuResponse {

    private Long id;
    private String name;
    private int cost;
    private int quantity;
    private MenuType menuTypeDto;
    private String image;

    public static MenuResponse of(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .menuTypeDto(menu.getType())
                .cost(menu.getCost())
                .quantity(menu.getQuantity())
                .image(menu.getImage())
                .build();
    }
}
