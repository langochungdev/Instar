package com.instar.service.impl;
import com.instar.dto.SavedPostDto;
import com.instar.entity.SavedPost;
import com.instar.entity.Post;
import com.instar.entity.User;
import com.instar.mapper.SavedPostMapper;
import com.instar.repository.SavedPostRepository;
import com.instar.repository.PostRepository;
import com.instar.repository.UserRepository;
import com.instar.service.SavedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedPostServiceImpl implements SavedPostService {
    private final SavedPostRepository savedPostRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SavedPostMapper savedPostMapper;

    @Override
    public SavedPostDto savePost(Integer userId, Integer postId) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        SavedPost e = SavedPost.builder()
                .user(user)
                .post(post)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        e = savedPostRepository.save(e);
        return savedPostMapper.toDto(e);
    }

    @Override
    public void unsavePost(Integer userId, Integer postId) {
        savedPostRepository.findAll().stream()
                .filter(s -> s.getUser().getId().equals(userId) && s.getPost().getId().equals(postId))
                .findFirst()
                .ifPresent(s -> savedPostRepository.deleteById(s.getId()));
    }

    @Override
    public List<SavedPostDto> findByUserId(Integer userId) {
        return savedPostRepository.findAll().stream()
                .filter(s -> s.getUser().getId().equals(userId))
                .map(savedPostMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPostSaved(Integer userId, Integer postId) {
        return savedPostRepository.findAll().stream()
                .anyMatch(s -> s.getUser().getId().equals(userId) && s.getPost().getId().equals(postId));
    }

    @Override
    public SavedPostDto save(Integer postId, Integer userId) {
        if (savedPostRepository.existsByPostId_IdAndUserId_Id(postId, userId)) return null;
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        SavedPost e = SavedPost.builder()
                .post(post)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        e = savedPostRepository.save(e);
        return savedPostMapper.toDto(e);
    }

    @Override
    public void unsave(Integer postId, Integer userId) {
        SavedPost e = savedPostRepository.findByPostId_IdAndUserId_Id(postId, userId).orElse(null);
        savedPostRepository.delete(e);
    }
}
