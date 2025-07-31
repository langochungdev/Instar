package com.instar.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUserDto {
    private Integer chatId;
    private Integer userId;
    private Boolean isAdmin;
}
