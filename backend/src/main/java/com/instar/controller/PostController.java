package com.instar.controller;

import com.instar.entity.Post;
import com.instar.repository.PostRepository;
import com.instar.entity.User;
import com.instar.repository.UserRepository;
import com.instar.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token))
            return ResponseEntity.status(401).body("Unauthorized");

        Integer userId;
        try {
            userId = Integer.parseInt(jwtUtil.extractUserId(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.status(401).body("User not found");

        Post post = Post.builder()
                .user(user)
                .content(postRequest.getContent())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
        post = postRepository.save(post);

        // trả về id để frontend dùng upload file tiếp
        return ResponseEntity.ok(new PostResponse(post.getId(), post.getContent(), post.getCreatedAt()));
    }

    @Getter
    @Setter
    public static class PostRequest {
        private String content;
    }
    @Getter @Setter @AllArgsConstructor
    public static class PostResponse {
        private Integer id;
        private String content;
        private LocalDateTime createdAt;
    }
}
