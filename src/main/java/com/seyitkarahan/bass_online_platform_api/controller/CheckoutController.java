package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.CheckoutRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CheckoutResponse;
import com.seyitkarahan.bass_online_platform_api.service.CheckoutService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public CheckoutResponse checkout(
            @AuthenticationPrincipal String userEmail,
            @RequestBody CheckoutRequest request
    ) {
        return checkoutService.checkout(userEmail, request);
    }
}
