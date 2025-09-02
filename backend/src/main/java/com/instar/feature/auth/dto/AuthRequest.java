package com.instar.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String deviceId;
    private String deviceName;
    private String pushToken;
}
