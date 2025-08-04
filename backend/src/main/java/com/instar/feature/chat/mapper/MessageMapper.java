package com.instar.feature.chat.mapper;

import com.instar.feature.chat.dto.MessageDto;
import com.instar.feature.chat.entity.Chat;
import com.instar.feature.chat.entity.Message;
import com.instar.feature.user.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageDto toDto(Message e) {
        return MessageDto.builder()
                .id(e.getId())
                .chatId(e.getChat().getId())
                .senderId(e.getSender().getId())
                .content(e.getContent())
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
                .createdAt(dto.getCreatedAt())
                .isRead(dto.getIsRead())
                .build();
    }
}
