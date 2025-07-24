package com.instar.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
