package com.instar.feature.post.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/post-media")
@RequiredArgsConstructor
public class PostMediaController {
    @GetMapping
    public String test() {
        return "test";
    }
}
