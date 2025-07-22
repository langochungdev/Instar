package com.instar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeviceDto {
    private Integer id;
    private Integer userId;
    private String deviceToken;
    private String deviceName;
    private LocalDateTime lastLogin;
    private Boolean isActive;
}
