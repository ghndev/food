package org.example.food.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.example.food.domain.Basket;
import org.example.food.domain.Member;
import org.example.food.domain.Order;
import org.example.food.domain.Restaurant;
import org.example.food.dto.PaymentRequest;
import org.example.food.dto.PaymentResponse;
import org.example.food.security.PrincipalDetails;
import org.example.food.service.BasketService;
import org.example.food.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PaymentIntentController {

    private final OrderService orderService;
    private final BasketService basketService;

    @Value("${stripe.api.public-key}")
    private String key;

    @GetMapping("/checkout/{price}")
    public String redirect() {
        return "redirect:/";
    }

    @PostMapping("/checkout/{price}")
    public String payment(@PathVariable int price,@RequestParam("time") String time, @AuthenticationPrincipal PrincipalDetails principalDetails , Model model) {

        Member member = principalDetails.getMember();
        Basket basket = basketService.findByMemberId(member.getId());

        if (basket == null || basket.getBasketMenus().isEmpty()) {
            return "redirect:/basket"; // 장바구니가 비어 있으면 장바구니 페이지로 리디렉션
        }

        // 레스토랑 정보 가져오기 (장바구니 항목 중 첫 번째 항목의 레스토랑)
        Restaurant restaurant = basket.getBasketMenus().get(0).getMenu().getRestaurant();

        // Order 엔티티 생성
        orderService.createOrder(restaurant, basket.getBasketMenus(), member.getId(), time);

        model.addAttribute("publicKey", key);
        model.addAttribute("amount", price);
        model.addAttribute("email", "abc@example.com");
        model.addAttribute("productName", "food");
        return "checkout";
    }

    @PostMapping("/create-payment-intent")
    @ResponseBody
    public PaymentResponse createPaymentIntent(@RequestBody PaymentRequest request)
            throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(request.getAmount() * 100L)
                        .putMetadata("productName",
                                "food")
                        .setCurrency("krw")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams
                                        .AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent intent =
                PaymentIntent.create(params);

        return new PaymentResponse(intent.getId(),
                intent.getClientSecret());
    }
}
