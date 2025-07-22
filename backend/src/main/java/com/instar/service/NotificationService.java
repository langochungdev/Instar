package com.instar.service;

import com.instar.dto.NotificationDto;
import java.util.List;

public interface NotificationService {
    List<NotificationDto> findByUserId(Integer userId);
    void markAsRead(Integer notificationId);
    void markRead(Integer notificationId);
}
