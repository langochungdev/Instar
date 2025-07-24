package com.instar.service.impl;
import com.instar.dto.NotificationDto;
import com.instar.mapper.NotificationMapper;
import com.instar.repository.NotificationRepository;
import com.instar.service.NotificationService;
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
        return notificationRepository.findAll().stream()
                .filter(n -> n.getUserId().getId().equals(userId))
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void markRead(Integer notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setIsRead(true);
            notificationRepository.save(n);
        });
    }
}
