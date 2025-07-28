package com.instar.service.impl;

import com.instar.dto.MessageDto;
import com.instar.entity.Message;
import com.instar.entity.User;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.MessageMapper;
import com.instar.repository.MessageRepository;
import com.instar.repository.UserRepository;
import com.instar.service.MessageService;
import com.instar.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto send(MessageDto dto) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!dto.getSenderId().equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        User sender = userRepository.findById(dto.getSenderId()).orElse(null);
        User receiver = userRepository.findById(dto.getReceiverId()).orElse(null);

        Message e = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .videoUrl(dto.getVideoUrl())
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        e = messageRepository.save(e);
        return messageMapper.toDto(e);
    }

    @Override
    public List<MessageDto> getConversation(Integer userId1, Integer userId2) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if ((!userId1.equals(currentUserId) && !userId2.equals(currentUserId)) && !admin) {
            throw new NoPermissionException();
        }
        return messageRepository.findAll().stream()
                .filter(m -> (
                        (m.getSender().getId().equals(userId1) && m.getReceiver().getId().equals(userId2)) ||
                                (m.getSender().getId().equals(userId2) && m.getReceiver().getId().equals(userId1))
                ))
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getConversations(Integer userId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        return messageRepository.findAll().stream()
                .filter(m -> m.getSender().getId().equals(userId) || m.getReceiver().getId().equals(userId))
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void markRead(Integer messageId) {
        Message e = messageRepository.findById(messageId).orElse(null);
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (e == null || (!e.getReceiver().getId().equals(currentUserId) && !admin)) {
            throw new NoPermissionException();
        }
        e.setIsRead(true);
        messageRepository.save(e);
    }
}
