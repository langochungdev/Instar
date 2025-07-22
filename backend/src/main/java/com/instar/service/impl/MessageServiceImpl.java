package com.instar.service.impl;

import com.instar.dto.MessageDto;
import com.instar.entity.Message;
import com.instar.entity.User;
import com.instar.exception.CustomException;
import com.instar.mapper.MessageMapper;
import com.instar.repository.MessageRepository;
import com.instar.repository.UserRepository;
import com.instar.service.MessageService;
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
    public MessageDto sendMessage(MessageDto messageDto) {
        User sender = userRepository.findById(messageDto.getSenderId()).orElse(null);
        User receiver = userRepository.findById(messageDto.getReceiverId()).orElse(null);
        Message message = messageMapper.toEntity(messageDto, sender, receiver);
        message = messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    @Override
    public List<MessageDto> getConversation(Integer userId1, Integer userId2) {
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
        return messageRepository.findAll().stream()
                .filter(m -> m.getSender().getId().equals(userId) || m.getReceiver().getId().equals(userId))
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsRead(Integer messageId) {
        messageRepository.findById(messageId).ifPresent(m -> {
            m.setIsRead(true);
            messageRepository.save(m);
        });
    }

    @Override
    public MessageDto send(MessageDto dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new CustomException("Người gửi không tồn tại"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new CustomException("Người nhận không tồn tại"));

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .videoUrl(dto.getVideoUrl())
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        message = messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    @Override
    public void markRead(Integer messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException("Tin nhắn không tồn tại"));
        message.setIsRead(true);
        messageRepository.save(message);
    }

}
