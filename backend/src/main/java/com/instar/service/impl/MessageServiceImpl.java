package com.instar.service.impl;
import com.instar.dto.MessageDto;
import com.instar.entity.Message;
import com.instar.entity.User;
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
    public MessageDto sendMessage(MessageDto dto) {
        User sender = userRepository.findById(dto.getSenderId()).orElse(null);
        User receiver = userRepository.findById(dto.getReceiverId()).orElse(null);
        Message e = messageMapper.toEntity(dto, sender, receiver);
        e = messageRepository.save(e);
        return messageMapper.toDto(e);
    }

    @Override
    public List<MessageDto> getConversation(Integer userId1, Integer userId2) {
        return messageRepository.findAll().stream()
                .filter(m -> (
                    (m.getSenderId().getId().equals(userId1) && m.getReceiverId().getId().equals(userId2)) ||
                    (m.getSenderId().getId().equals(userId2) && m.getReceiverId().getId().equals(userId1))
                ))
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getConversations(Integer userId) {
        return messageRepository.findAll().stream()
                .filter(m -> m.getSenderId().getId().equals(userId) || m.getReceiverId().getId().equals(userId))
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto send(MessageDto dto) {
        User sender = userRepository.findById(dto.getSenderId()).orElse(null);
        User receiver = userRepository.findById(dto.getReceiverId()).orElse(null);

        Message e = Message.builder()
                .senderId(sender)
                .receiverId(receiver)
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
    public void markRead(Integer messageId) {
        Message e = messageRepository.findById(messageId).orElse(null);
        e.setIsRead(true);
        messageRepository.save(e);
    }

}
