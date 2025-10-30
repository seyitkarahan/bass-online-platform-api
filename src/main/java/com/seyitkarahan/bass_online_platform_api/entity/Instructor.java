package com.seyitkarahan.bass_online_platform_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;

    private String expertiseArea;

    private int experienceYears;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
