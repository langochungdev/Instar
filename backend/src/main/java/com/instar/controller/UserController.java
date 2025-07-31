package com.instar.controller;
import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.ChangePasswordRequest;
import com.instar.dto.UserDto;
import com.instar.entity.User;
import com.instar.repository.UserRepository;
import com.instar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

//    @PostMapping("/register")
//    public UserDto register(@RequestBody User e) {
//        System.out.println("username: " + e.getUsername());
//        System.out.println("email: " + e.getEmail());
//        System.out.println("password: " + e.getPassword());
//        System.out.println("fullName: " + e.getFullName());
//        return userService.register(e);
//    }
//
//
//    @PostMapping("/login")
//    public AuthResponse login(@RequestBody AuthRequest request) {
//        User e = userRepository.findByUsername(request.getUsername()).orElse(null);
//        if (e != null) {
//            System.out.println("Đăng nhập thành công: username = " + e.getUsername() + ", role = " + e.getRole());
//        }
//        return userService.login(request);
//    }


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
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
