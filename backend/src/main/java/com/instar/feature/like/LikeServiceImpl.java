package com.instar.feature.like;

import com.instar.common.exception.NoPermissionException;
import com.instar.common.util.CurrentUserUtil;
import com.instar.feature.post.model.post.Post;
import com.instar.feature.post.model.post.PostRepository;
import com.instar.feature.user.User;
import com.instar.feature.user.UserRepository;
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
    public LikeDto like(Integer userId, Integer postId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        if (likeRepository.existsByPostId_IdAndUserId_Id(postId, userId)) return null;
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        Like e = Like.builder()
                .post(post)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        e = likeRepository.save(e);
        return likeMapper.toDto(e);
    }

    @Override
    public void unlike(Integer postId, Integer userId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        Like e = likeRepository.findByPostId_IdAndUserId_Id(postId, userId).orElse(null);
        if (e != null) {
            likeRepository.delete(e);
        }
    }
}
