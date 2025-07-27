package com.instar.controller;
import com.instar.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{userId}/follow")
    public String follow(@PathVariable Integer userId, @RequestParam Integer followerId) {
        followService.follow(userId, followerId);
        return "Đã follow";
    }

    @DeleteMapping("/{userId}/unfollow")
    public String unfollow(@PathVariable Integer userId, @RequestParam Integer followerId) {
        followService.unfollow(userId, followerId);
        return "Đã unfollow";
    }
}
