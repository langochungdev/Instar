package com.instar.feature.social.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {
    @GetMapping
    public String test() {
        return "test";
    }
}
