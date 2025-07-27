package com.instar.controller;
import com.instar.dto.NotificationDto;
import com.instar.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public List<NotificationDto> getNotifications(@PathVariable Integer userId) {
        return notificationService.findByUserId(userId);
    }

    @PutMapping("/{id}/read")
    public String markRead(@PathVariable Integer id) {
        notificationService.markRead(id);
        return "Đã đọc thông báo";
    }
}
