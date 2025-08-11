package com.instar.feature.auth;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    private String username;
    private String password;
    private String deviceToken;
    private String deviceName;
    private String fingerprint;

}
