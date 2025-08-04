package com.instar.feature.notification;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> findByUserId(Integer userId);
    void markRead(Integer notificationId);
}
