package com.instar.feature.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {
    private Integer id;
    private Integer userId;
    private String deviceToken;
    private String deviceName;
    private LocalDateTime lastLogin;
    private Boolean isActive;
}
