package com.instar.feature.user.controller;

import com.instar.feature.user.dto.ChangePasswordRequestDto;
import com.instar.feature.user.dto.UserDto;
import com.instar.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/password")
    public String changePassword(@PathVariable UUID id, @RequestBody ChangePasswordRequestDto req) {
        userService.changePassword(id, req.getOldPassword(), req.getNewPassword());
        return "Đổi mật khẩu thành công";
    }

    @PostMapping("/{id}/verify")
    public String verify(@PathVariable UUID id, @RequestParam String code) {
        userService.verifyAccount(id, code);
        return "Kích hoạt tài khoản thành công";
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> checkStatus() {
        return ResponseEntity.ok(userService.checkStatus());
    }

}
