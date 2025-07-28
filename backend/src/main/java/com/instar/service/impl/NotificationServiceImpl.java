package com.instar.service.impl;
import com.instar.dto.NotificationDto;
import com.instar.entity.Notification;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.NotificationMapper;
import com.instar.repository.NotificationRepository;
import com.instar.service.NotificationService;
import com.instar.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationDto> findByUserId(Integer userId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        return notificationRepository.findAll().stream()
                .filter(n -> n.getUser().getId().equals(userId))
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void markRead(Integer notificationId) {
        Notification n = notificationRepository.findById(notificationId).orElse(null);
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (n == null || (!n.getUser().getId().equals(currentUserId) && !admin)) {
            throw new NoPermissionException();
        }
        n.setIsRead(true);
        notificationRepository.save(n);
    }
}
