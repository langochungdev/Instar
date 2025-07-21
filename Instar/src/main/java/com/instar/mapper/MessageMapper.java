package com.instar.mapper;

import com.instar.entity.Message;
import com.instar.dto.MessageDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageDto toDto(Message entity) {
        if (entity == null) return null;
        return MessageDto.builder()
                .id(entity.getId())
                .senderId(entity.getSender() != null ? entity.getSender().getId() : null)
                .receiverId(entity.getReceiver() != null ? entity.getReceiver().getId() : null)
                .content(entity.getContent())
                .imageUrl(entity.getImageUrl())
                .videoUrl(entity.getVideoUrl())
                .createdAt(entity.getCreatedAt())
                .isRead(entity.getIsRead())
                .build();
    }

    public Message toEntity(MessageDto dto, User sender, User receiver) {
        if (dto == null) return null;
        return Message.builder()
                .id(dto.getId())
                .sender(sender)
                .receiver(receiver)
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .videoUrl(dto.getVideoUrl())
                .createdAt(dto.getCreatedAt())
                .isRead(dto.getIsRead())
                .build();
    }
}
