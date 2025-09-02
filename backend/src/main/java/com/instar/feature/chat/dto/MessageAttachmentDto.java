package com.instar.feature.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageAttachmentDto {
    private UUID id;
    private UUID messageId;
    private String fileUrl;
    private String fileType;
}
