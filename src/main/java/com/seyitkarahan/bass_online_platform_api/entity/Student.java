package com.seyitkarahan.bass_online_platform_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String educationLevel; // örn: "Lisans", "Yüksek Lisans"

    private String schoolName;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
