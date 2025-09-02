package com.instar.feature.post.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    @GetMapping
    public String test() {
        return "test";
    }
}
