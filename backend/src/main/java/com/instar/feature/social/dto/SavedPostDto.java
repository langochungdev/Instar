package com.instar.feature.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedPostDto {
    private UUID id;
    private UUID userId;
    private UUID postId;
    private LocalDateTime createdAt;
}
