package com.instar.feature.savedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {
    private final SavedPostService savedPostService;

    @PostMapping("/post/{postId}/save")
    public String save(@PathVariable Integer postId, @RequestParam Integer userId) {
        savedPostService.save(postId, userId);
        return "Đã lưu bài viết";
    }

    @DeleteMapping("/post/{postId}/unsave")
    public String unsave(@PathVariable Integer postId, @RequestParam Integer userId) {
        savedPostService.unsave(postId, userId);
        return "Đã bỏ lưu";
    }
}
