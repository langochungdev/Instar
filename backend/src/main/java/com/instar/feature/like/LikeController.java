package com.instar.feature.like;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{userId}/like/{postId}")
    public String like(@PathVariable Integer userId, @PathVariable Integer postId) {
        likeService.like(userId, postId);
        return "Đã like";
    }

    @DeleteMapping("/post/{postId}")
    public String unlike(@PathVariable Integer postId, @RequestParam Integer userId) {
        likeService.unlike(postId, userId);
        return "Đã bỏ like";
    }
}
