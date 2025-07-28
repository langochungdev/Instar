package com.instar.service.impl;

import com.instar.dto.SavedPostDto;
import com.instar.entity.SavedPost;
import com.instar.entity.Post;
import com.instar.entity.User;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.SavedPostMapper;
import com.instar.repository.SavedPostRepository;
import com.instar.repository.PostRepository;
import com.instar.repository.UserRepository;
import com.instar.service.SavedPostService;
import com.instar.util.CurrentUserUtil;
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
    public List<SavedPostDto> findByUserId(Integer userId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        return savedPostRepository.findAll().stream()
                .filter(s -> s.getUser().getId().equals(userId))
                .map(savedPostMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPostSaved(Integer userId, Integer postId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        return savedPostRepository.findAll().stream()
                .anyMatch(s -> s.getUser().getId().equals(userId) && s.getPost().getId().equals(postId));
    }

    @Override
    public SavedPostDto save(Integer postId, Integer userId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
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
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        SavedPost e = savedPostRepository.findByPostId_IdAndUserId_Id(postId, userId).orElse(null);
        if (e != null) {
            savedPostRepository.delete(e);
        }
    }
}
