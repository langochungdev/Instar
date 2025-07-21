package com.instar.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPassword; // nếu cần xác thực lại mật khẩu cũ, có thể bỏ nếu không cần
    private String newPassword;
}
