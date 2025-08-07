package com.instar.feature.auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthDto {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String role;
}
