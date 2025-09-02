package com.instar.feature.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private UUID id;
    private UUID chatId;
    private UUID senderId;
    private UUID receiverId;
    private String content;
    private LocalDateTime createdAt;
    private boolean isRead;
    private List<MessageAttachmentDto> attachments;
}
