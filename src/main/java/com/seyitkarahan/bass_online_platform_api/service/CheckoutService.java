package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.CheckoutRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CheckoutResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Enrollment;
import com.seyitkarahan.bass_online_platform_api.entity.Payment;
import com.seyitkarahan.bass_online_platform_api.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    public CheckoutService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            EnrollmentRepository enrollmentRepository,
            PaymentRepository paymentRepository,
            StudentRepository studentRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    public CheckoutResponse checkout(String userEmail, CheckoutRequest request) {

        Long studentId = studentRepository
                .findStudentIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Long cartId = cartRepository.findCartIdByStudent(studentId);
        if (cartId == null) {
            throw new RuntimeException("Cart is empty");
        }

        var items = cartItemRepository.findItems(cartId);
        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = items.stream()
                .map(item -> BigDecimal.valueOf(item.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Payment payment = Payment.builder()
                .studentId(studentId)
                .amount(totalAmount)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus("SUCCESS") // şimdilik fake
                .paymentDate(LocalDateTime.now())
                .build();

        Long paymentId = paymentRepository.save(payment);

        for (var item : items) {

            if (enrollmentRepository.exists(studentId, item.getCourseId())) {
                continue;
            }

            enrollmentRepository.save(
                    studentId,
                    item.getCourseId()
            );
        }


        cartItemRepository.clearCart(cartId);

        return CheckoutResponse.builder()
                .paymentId(paymentId)
                .totalAmount(totalAmount)
                .paymentStatus("SUCCESS")
                .build();
    }
}
