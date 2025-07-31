package com.instar.mapper;

import com.instar.entity.Message;
import com.instar.dto.MessageDto;
import com.instar.entity.User;
import com.instar.entity.Chat;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageDto toDto(Message e) {
        return MessageDto.builder()
                .id(e.getId())
                .chatId(e.getChat().getId())
                .senderId(e.getSender().getId())
                .content(e.getContent())
                .imageUrl(e.getImageUrl())
                .videoUrl(e.getVideoUrl())
                .createdAt(e.getCreatedAt())
                .isRead(e.getIsRead())
                .build();
    }

    public Message toEntity(MessageDto dto, User sender, Chat chat) {
        return Message.builder()
                .id(dto.getId())
                .chat(chat)
                .sender(sender)
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .videoUrl(dto.getVideoUrl())
                .createdAt(dto.getCreatedAt())
                .isRead(dto.getIsRead())
                .build();
    }
}
