package com.instar.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private Integer id;
    private Integer userId;
    private String type;
    private String message;
    private String link;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
