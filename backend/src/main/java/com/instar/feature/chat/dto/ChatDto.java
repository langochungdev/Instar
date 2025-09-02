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
public class ChatDto {
    private UUID id;
    private String chatName;
    private boolean isGroup;
    private LocalDateTime createdAt;
    private UUID createdById;
    private List<UUID> memberIds;
}
