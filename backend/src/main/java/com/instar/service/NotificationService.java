package com.instar.service;

import com.instar.dto.NotificationDto;
import java.util.List;

public interface NotificationService {
    List<NotificationDto> findByUserId(Integer userId);
    void markRead(Integer notificationId);
}
