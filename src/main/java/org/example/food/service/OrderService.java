package org.example.food.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.food.domain.*;
import org.example.food.repository.MenuRepository;
import org.example.food.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BasketService basketService;

    @Transactional
    public Order createOrder(Restaurant restaurant, List<BasketMenu> basketMenus, Long memberId, String time) {
        Basket basket = basketService.findByMemberId(memberId);

        Order order = Order.builder()
                .restaurant(restaurant)
                .member(basket.getMember())
                .time(time)
                .build();

        for (BasketMenu basketMenu : basketMenus) {
            OrderMenu orderMenu = OrderMenu.builder()
                    .order(order)
                    .menu(basketMenu.getMenu())
                    .quantity(basketMenu.getQuantity())
                    .build();
            order.addOrderMenu(orderMenu);
        }
        order =  orderRepository.save(order);

        basket.getBasketMenus().clear();
        basketService.save(basket);

        return order;
    }

    public List<Order> getPendingOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, OrderStatus.PENDING);
    }

    @Transactional
    public void completeOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        order.setStatus(OrderStatus.COMPLETED);
    }
}
