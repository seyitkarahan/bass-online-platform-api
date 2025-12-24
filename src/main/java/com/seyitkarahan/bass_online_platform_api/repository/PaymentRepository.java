package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Payment payment) {
        String sql = """
            INSERT INTO payments
            (enrollment_id, amount, payment_method, payment_status)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                payment.getStudentId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getPaymentDate()
        );
    }
}

