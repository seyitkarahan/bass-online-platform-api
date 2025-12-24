package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.PurchaseRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.PurchaseResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Payment;
import com.seyitkarahan.bass_online_platform_api.repository.CourseRepository;
import com.seyitkarahan.bass_online_platform_api.repository.EnrollmentRepository;
import com.seyitkarahan.bass_online_platform_api.repository.PaymentRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PurchaseService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;

    public PurchaseService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            PaymentRepository paymentRepository,
            CourseRepository courseRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public PurchaseResponse purchase(
            PurchaseRequest request,
            String userEmail
    ) {

        Long studentId = studentRepository
                .findStudentIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (enrollmentRepository.exists(studentId, request.getCourseId())) {
            throw new RuntimeException("Already enrolled");
        }

        Long enrollmentId = enrollmentRepository
                .save(studentId, request.getCourseId());

        BigDecimal price = courseRepository
                .getPrice(request.getCourseId());

        Payment payment = Payment.builder()
                .studentId(studentId)
                .amount(price)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus("SUCCESS")
                .paymentDate(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        return new PurchaseResponse(
                enrollmentId,
                request.getCourseId(),
                price,
                "SUCCESS"
        );
    }
}
