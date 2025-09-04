package com.instar.feature.auth.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;

    @JsonIgnore
    private String refreshToken;

    @Builder.Default
    private String token_type = "Bearer";
    private Long expiresIn;
    private Long refreshExpiresIn;
    private User user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class User {
        private UUID id;
        private String username;
        private String email;
        private String fullName;
        private Set<String> roles;
    }
}

