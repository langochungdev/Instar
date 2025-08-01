package com.instar.controller;

import com.instar.dto.PostMediaDto;
import com.instar.entity.Post;
import com.instar.entity.PostMedia;
import com.instar.entity.User;
import com.instar.mapper.PostMediaMapper;
import com.instar.repository.PostMediaRepository;
import com.instar.repository.PostRepository;
import com.instar.repository.UserRepository;
import com.instar.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post-media")
@RequiredArgsConstructor
public class PostMediaController {
    private final PostMediaRepository postMediaRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMediaMapper postMediaMapper;
    private final JwtUtil jwtUtil;

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("postId") Integer postId,
            @RequestParam("files") MultipartFile[] files,
            HttpServletRequest request
    ) throws IOException {
        // Lấy access token từ request header
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token))
            return ResponseEntity.status(401).body("Unauthorized");

        // Lấy userId từ token
        Integer userId;
        try {
            userId = Integer.parseInt(jwtUtil.extractUserId(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.status(401).body("User not found");

        // Kiểm tra quyền sở hữu post
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null)
            return ResponseEntity.badRequest().body("Post not found");
        if (!post.getUser().getId().equals(user.getId()))
            return ResponseEntity.status(403).body("Bạn không có quyền upload media cho post này");

        // Đảm bảo thư mục uploads tồn tại
        File uploadPath = new File(System.getProperty("user.dir") + File.separator + uploadDir);
        if (!uploadPath.exists()) {
            boolean ok = uploadPath.mkdirs();
            if (!ok) return ResponseEntity.status(500).body("Không tạo được thư mục uploads, kiểm tra quyền ghi.");
        }

        // Lưu từng file, trả về list DTO
        List<PostMediaDto> dtos = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String filename = System.currentTimeMillis() + "_" + originalFilename;
            File dest = new File(uploadPath, filename);
            file.transferTo(dest);

            String type = (file.getContentType() != null && file.getContentType().startsWith("video")) ? "video" : "image";
            PostMedia postMedia = PostMedia.builder()
                    .post(post)
                    .mediaUrl(filename)
                    .mediaType(type)
                    .build();
            postMedia = postMediaRepository.save(postMedia);
            dtos.add(postMediaMapper.toDto(postMedia));
        }
        return ResponseEntity.ok(dtos);
    }
}
