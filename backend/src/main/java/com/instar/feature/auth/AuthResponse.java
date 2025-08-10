package com.instar.feature.auth;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private Long expiresIn;
    private User user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class User {
        private Integer id;
        private String username;
        private String email;
        private String fullName;
        private String role;
    }
}

