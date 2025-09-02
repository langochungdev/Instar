package com.instar.feature.social.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {
    @GetMapping
    public String test() {
        return "test";
    }
}
