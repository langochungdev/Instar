package com.instar.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatDto {
    private Integer id;
    private String chatName;
    private Boolean isGroup;
    private Integer createdById;
    private LocalDateTime createdAt;
    private List<Integer> memberIds; // danh sách user id trong nhóm
}
