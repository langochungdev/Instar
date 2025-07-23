package com.instar.mapper;
import com.instar.entity.Message;
import com.instar.dto.MessageDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageDto toDto(Message e) {
        return MessageDto.builder()
                .id(e.getId())
                .senderId(e.getSenderId().getId())
                .receiverId(e.getReceiverId().getId())
                .content(e.getContent())
                .imageUrl(e.getImageUrl())
                .videoUrl(e.getVideoUrl())
                .createdAt(e.getCreatedAt())
                .isRead(e.getIsRead())
                .build();
    }

    public Message toEntity(MessageDto dto, User sender, User receiver) {
        return Message.builder()
                .id(dto.getId())
                .senderId(sender)
                .receiverId(receiver)
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .videoUrl(dto.getVideoUrl())
                .createdAt(dto.getCreatedAt())
                .isRead(dto.getIsRead())
                .build();
    }
}
