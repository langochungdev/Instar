package com.instar.service.impl;
import com.instar.dto.PostDto;
import com.instar.entity.Post;
import com.instar.entity.User;
import com.instar.mapper.PostMapper;
import com.instar.repository.PostRepository;
import com.instar.repository.UserRepository;
import com.instar.service.PostService;
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
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Post e = postMapper.toEntity(dto, user);
        e = postRepository.save(e);
        return postMapper.toDto(e);
    }

    @Override
    public PostDto update(Integer id, PostDto dto) {
        Post e = postRepository.findById(id).orElse(null);
        if (e == null) return null;
        e.setContent(dto.getContent());
        e.setImageUrl(dto.getImageUrl());
        e.setVideoUrl(dto.getVideoUrl());
        e.setUpdatedAt(dto.getUpdatedAt());
        e.setIsDeleted(dto.getIsDeleted());
        e = postRepository.save(e);
        return postMapper.toDto(e);
    }

    @Override
    public void delete(Integer id) {
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
