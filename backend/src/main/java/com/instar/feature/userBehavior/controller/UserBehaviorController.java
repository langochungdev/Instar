package com.instar.feature.userBehavior.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-behaviors")
@RequiredArgsConstructor
public class UserBehaviorController {

    @GetMapping
    public String test() {
        return "test";
    }
}
