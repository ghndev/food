package org.example.food.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketMenu> basketMenus = new ArrayList<>();

    @Builder
    public Basket(Member member) {
        this.member = member;
    }

    public void addBasketMenu(BasketMenu basketMenu) {
        basketMenus.add(basketMenu);
        basketMenu.setBasket(this);
    }

    public void removeBasketMenu(BasketMenu basketMenu) {
        basketMenus.remove(basketMenu);
        basketMenu.setBasket(null);
    }
}
