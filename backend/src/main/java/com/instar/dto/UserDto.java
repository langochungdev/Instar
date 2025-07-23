package com.instar.dto;
//import com.instar.validation.UniqueEmail;
//import com.instar.validation.UniqueUsername;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
//    @UniqueUsername
    private String username;
//    @UniqueEmail
    private String email;
    private String fullname;
    private String avatarUrl;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Boolean isActive;
    private Boolean isVerified;
}
