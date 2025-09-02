package com.instar.feature.chat.mapper;

import com.instar.feature.chat.dto.MessageAttachmentDto;
import com.instar.feature.chat.dto.MessageDto;
import com.instar.feature.chat.entity.Chat;
import com.instar.feature.chat.entity.Message;
import com.instar.feature.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final MessageAttachmentMapper attachmentMapper;

    public MessageDto toDto(Message e) {
        List<MessageAttachmentDto> attachments = e.getAttachments() != null
                ? e.getAttachments().stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList())
                : List.of();

        return MessageDto.builder()
                .id(e.getId())
                .chatId(e.getChat().getId())
                .senderId(e.getSender().getId())
                .content(e.getContent())
                .createdAt(e.getCreatedAt())
                .isRead(e.isRead())
                .attachments(attachments)
                .build();
    }

    public Message toEntity(MessageDto dto, User sender, Chat chat) {
        return Message.builder()
                .id(dto.getId())
                .chat(chat)
                .sender(sender)
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now())
                .isRead(dto.isRead())
                .build();
    }
}
