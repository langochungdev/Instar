package com.instar.feature.post.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    @GetMapping
    public String test() {
        return "test";
    }
}
