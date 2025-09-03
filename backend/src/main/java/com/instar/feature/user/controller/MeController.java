package com.instar.feature.user.controller;
import com.instar.common.util.CurrentUserUtil;
import com.instar.feature.user.dto.UserDto;
import com.instar.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MeController {
    private final UserService userService;
    private final CurrentUserUtil currentUserUtil;

    @GetMapping
    public ResponseEntity<UserDto> checkStatus() {
        UUID userId = currentUserUtil.getUser().getId();
        return ResponseEntity.ok(userService.checkStatus(userId));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserDto request) {
        UUID userId = currentUserUtil.getUser().getId();
        return ResponseEntity.ok(userService.updateProfile(userId, request));
    }

}
