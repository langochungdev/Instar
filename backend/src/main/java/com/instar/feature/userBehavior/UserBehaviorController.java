package com.instar.feature.userBehavior;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-behaviors")
@RequiredArgsConstructor
public class UserBehaviorController {
    private final UserBehaviorService userBehaviorService;

    @PostMapping
    public UserBehaviorDto log(@RequestBody UserBehaviorDto dto) {
        return userBehaviorService.logBehavior(dto);
    }

    @GetMapping("/user/{userId}")
    public List<UserBehaviorDto> getByUser(@PathVariable Integer userId) {
        return userBehaviorService.findByUserId(userId);
    }
}
