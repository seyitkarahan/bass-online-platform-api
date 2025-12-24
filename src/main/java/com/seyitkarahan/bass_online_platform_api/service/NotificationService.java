package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.NotificationCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.NotificationResponse;
import com.seyitkarahan.bass_online_platform_api.repository.NotificationRepository;
import com.seyitkarahan.bass_online_platform_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void createNotification(NotificationCreateRequest request) {
        notificationRepository.save(
                request.getUserId(),
                request.getMessage(),
                request.getNotifyAt()
        );
    }

    public List<NotificationResponse> getMyNotifications(String userEmail) {

        Long userId = userRepository
                .findIdByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByUser(userId);
    }
}

