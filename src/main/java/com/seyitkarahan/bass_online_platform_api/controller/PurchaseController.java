package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.PurchaseRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.PurchaseResponse;
import com.seyitkarahan.bass_online_platform_api.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/purchase")
@PreAuthorize("hasRole('STUDENT')")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> purchase(
            @RequestBody @Valid PurchaseRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                purchaseService.purchase(
                        request,
                        authentication.getName()
                )
        );
    }
}
