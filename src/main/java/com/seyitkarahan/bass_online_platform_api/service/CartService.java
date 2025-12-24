package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.CartItemAddRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CartItemResponse;
import com.seyitkarahan.bass_online_platform_api.dto.response.CartResponse;
import com.seyitkarahan.bass_online_platform_api.repository.CartItemRepository;
import com.seyitkarahan.bass_online_platform_api.repository.CartRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final StudentRepository studentRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            StudentRepository studentRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.studentRepository = studentRepository;
    }

    public void addToCart(String userEmail, CartItemAddRequest request) {
        Long studentId = studentRepository
                .findStudentIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Long cartId = cartRepository.findCartIdByStudent(studentId);

        if (cartId == null) {
            cartId = cartRepository.createCart(studentId);
        }

        if (cartItemRepository.exists(cartId, request.getCourseId())) {
            throw new RuntimeException("Course already in cart");
        }

        cartItemRepository.addItem(cartId, request.getCourseId());
    }

    public CartResponse getCart(String userEmail) {
        Long studentId = studentRepository
                .findStudentIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Long cartId = cartRepository.findCartIdByStudent(studentId);

        if (cartId == null) {
            return new CartResponse(
                    null,
                    List.of(),
                    BigDecimal.ZERO
            );
        }

        var items = cartItemRepository.findItems(cartId);

        BigDecimal total = items.stream()
                .map(item -> BigDecimal.valueOf(item.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(cartId, items, total);
    }


    public void removeFromCart(Long cartItemId) {
        cartItemRepository.removeItem(cartItemId);
    }
}
