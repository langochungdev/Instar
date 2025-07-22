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
    public PostDto create(PostDto postDto) {
        User user = userRepository.findById(postDto.getUserId()).orElse(null);
        Post post = postMapper.toEntity(postDto, user);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    public PostDto update(Integer id, PostDto postDto) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) return null;
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());
        post.setVideoUrl(postDto.getVideoUrl());
        post.setUpdatedAt(postDto.getUpdatedAt());
        post.setIsDeleted(postDto.getIsDeleted());
        post = postRepository.save(post);
        return postMapper.toDto(post);
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
