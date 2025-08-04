package com.instar.feature.chat.mapper;

import com.instar.feature.chat.dto.MessageAttachmentDto;
import com.instar.feature.chat.entity.Message;
import com.instar.feature.chat.entity.MessageAttachment;
import org.springframework.stereotype.Component;

@Component
public class MessageAttachmentMapper {
    public MessageAttachmentDto toDto(MessageAttachment e) {
        return MessageAttachmentDto.builder()
                .id(e.getId())
                .messageId(e.getMessage().getId())
                .fileUrl(e.getFileUrl())
                .fileType(e.getFileType())
                .build();
    }

    public MessageAttachment toEntity(MessageAttachmentDto dto, Message message) {
        return MessageAttachment.builder()
                .id(dto.getId())
                .message(message)
                .fileUrl(dto.getFileUrl())
                .fileType(dto.getFileType())
                .build();
    }
}
