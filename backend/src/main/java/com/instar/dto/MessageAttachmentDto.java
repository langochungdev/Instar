package com.instar.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageAttachmentDto {
    private Integer id;
    private Integer messageId;
    private String fileUrl;
    private String fileType;
}
