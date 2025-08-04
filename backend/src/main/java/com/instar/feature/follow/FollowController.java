package com.instar.feature.follow;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{followerId}/follow/{followingId}")
    public String follow(@PathVariable Integer followerId, @PathVariable Integer followingId) {
        followService.follow(followerId, followingId);
        return "Đã follow";
    }

    @DeleteMapping("/{followerId}/unfollow/{followingId}")
    public String unfollow(@PathVariable Integer followerId, @RequestParam Integer followingId) {
        followService.unfollow(followerId, followingId);
        return "Đã unfollow";
    }
}
