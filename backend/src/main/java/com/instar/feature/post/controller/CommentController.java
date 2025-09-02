package com.instar.feature.post.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    @GetMapping
    public String test() {
        return "test";
    }
}
