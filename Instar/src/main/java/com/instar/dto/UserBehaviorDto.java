package com.instar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserBehaviorDto {
    private Integer id;
    private Integer userId;
    private String action;
    private Integer targetId;
    private LocalDateTime createdAt;
}
