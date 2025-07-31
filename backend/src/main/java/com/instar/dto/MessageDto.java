package com.instar.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private Integer id;
    private Integer chatId;
    private Integer senderId;
    private String content;
    private String imageUrl;
    private String videoUrl;
    private LocalDateTime createdAt;
    private Boolean isRead;
}
