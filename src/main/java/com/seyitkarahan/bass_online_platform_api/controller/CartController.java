package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.CartItemAddRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CartResponse;
import com.seyitkarahan.bass_online_platform_api.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public void addToCart(
            @RequestBody CartItemAddRequest request,
            Authentication authentication
    ) {
        cartService.addToCart(authentication.getName(), request);
    }

    @GetMapping
    public CartResponse getCart(Authentication authentication) {
        return cartService.getCart(authentication.getName());
    }

    @DeleteMapping("/items/{id}")
    public void removeItem(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }
}
