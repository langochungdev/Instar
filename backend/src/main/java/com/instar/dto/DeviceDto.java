package com.instar.dto;
import lombok.*;
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
