package com.instar.dto;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private Integer id;
    private Integer chatId;
    private Integer senderId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isRead;
    private List<MessageAttachmentDto> attachments;
}
