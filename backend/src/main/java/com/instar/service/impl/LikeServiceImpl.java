package com.instar.service.impl;

import com.instar.dto.LikeDto;
import com.instar.entity.Like;
import com.instar.entity.Post;
import com.instar.entity.User;
import com.instar.exception.CustomException;
import com.instar.mapper.LikeMapper;
import com.instar.repository.LikeRepository;
import com.instar.repository.PostRepository;
import com.instar.repository.UserRepository;
import com.instar.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeMapper likeMapper;

    @Override
    public LikeDto likePost(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        Like like = Like.builder()
                .post(post)
                .user(user)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        like = likeRepository.save(like);
        return likeMapper.toDto(like);
    }

    @Override
    public void unlikePost(Integer postId, Integer userId) {
        likeRepository.findAll().stream()
                .filter(l -> l.getPost().getId().equals(postId) && l.getUser().getId().equals(userId))
                .findFirst()
                .ifPresent(l -> likeRepository.deleteById(l.getId()));
    }

    @Override
    public List<LikeDto> findByPostId(Integer postId) {
        return likeRepository.findAll().stream()
                .filter(l -> l.getPost().getId().equals(postId))
                .map(likeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUserLikedPost(Integer postId, Integer userId) {
        return likeRepository.findAll().stream()
                .anyMatch(l -> l.getPost().getId().equals(postId) && l.getUser().getId().equals(userId));
    }

    @Override
    public LikeDto like(Integer postId, Integer userId) {
        // Kiểm tra đã like chưa
        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new CustomException("Đã like post này rồi!");
        }
        // Lấy post và user
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post không tồn tại"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User không tồn tại"));
        // Tạo like mới
        Like like = Like.builder()
                .post(post)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        like = likeRepository.save(like);
        return likeMapper.toDto(like);
    }

    @Override
    public void unlike(Integer postId, Integer userId) {
        Like like = likeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new CustomException("Like không tồn tại"));
        likeRepository.delete(like);
    }

}
