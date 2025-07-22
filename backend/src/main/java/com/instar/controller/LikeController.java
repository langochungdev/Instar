package com.instar.controller;

import com.instar.dto.LikeDto;
import com.instar.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    public String like(@PathVariable Integer postId, @RequestParam Integer userId) {
        likeService.like(postId, userId);
        return "Đã like";
    }

    @DeleteMapping("/post/{postId}")
    public String unlike(@PathVariable Integer postId, @RequestParam Integer userId) {
        likeService.unlike(postId, userId);
        return "Đã bỏ like";
    }
}
