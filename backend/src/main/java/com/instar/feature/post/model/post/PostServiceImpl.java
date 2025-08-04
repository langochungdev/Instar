package com.instar.feature.post.model.post;

import com.instar.common.exception.NoPermissionException;
import com.instar.common.util.CurrentUserUtil;
import com.instar.feature.user.User;
import com.instar.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public PostDto findById(Integer id) {
        return postRepository.findById(id)
                .map(postMapper::toDto)
                .orElse(null);
    }

    @Override
    public PostDto create(PostDto dto) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!dto.getUserId().equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Post e = postMapper.toEntity(dto, user);
        e = postRepository.save(e);
        return postMapper.toDto(e);
    }


    @Override
    public PostDto update(Integer id, PostDto dto) {
        Post e = postRepository.findById(id).orElse(null);
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (e == null) {
            System.out.println("Không tìm thấy bài viết id: " + id);
        } else {
            System.out.println("UserID chủ bài viết: " + e.getUser().getId());
        }
        if (e == null || (!e.getUser().getId().equals(currentUserId) && !admin)) {
            throw new NoPermissionException();
        }
        e.setContent(dto.getContent());
        e.setUpdatedAt(dto.getUpdatedAt());
        e.setIsDeleted(dto.getIsDeleted());
        e = postRepository.save(e);
        return postMapper.toDto(e);
    }

    @Override
    public void delete(Integer id) {
        Post e = postRepository.findById(id).orElse(null);
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (e == null || (!e.getUser().getId().equals(currentUserId) && !admin)) {
            throw new NoPermissionException();
        }
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> findAll() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findByUserId(Integer userId) {
        return postRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(userId))
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }
}
