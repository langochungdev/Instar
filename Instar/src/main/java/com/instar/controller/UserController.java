package com.instar.controller;

import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.ChangePasswordRequest;
import com.instar.dto.UserDto;
import com.instar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return userService.login(request);
    }


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Integer id, @RequestBody UserDto dto) {
        return userService.update(id, dto);
    }


    @PutMapping("/{id}/password")
    public String changePassword(@PathVariable Integer id, @RequestBody ChangePasswordRequest req) {
        userService.changePassword(id, req.getOldPassword(), req.getNewPassword());
        return "Đổi mật khẩu thành công";
    }


    @PostMapping("/{id}/verify")
    public String verify(@PathVariable Integer id, @RequestParam String code) {
        userService.verifyAccount(id, code);
        return "Kích hoạt tài khoản thành công";
    }
}
