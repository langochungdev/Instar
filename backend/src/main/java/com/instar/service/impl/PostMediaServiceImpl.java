package com.instar.service.impl;

import com.instar.dto.PostMediaDto;
import com.instar.entity.Post;
import com.instar.entity.PostMedia;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.PostMediaMapper;
import com.instar.repository.PostMediaRepository;
import com.instar.repository.PostRepository;
import com.instar.service.PostMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostMediaServiceImpl implements PostMediaService {
    private final PostMediaRepository postMediaRepository;
    private final PostRepository postRepository;
    private final PostMediaMapper postMediaMapper;

    @Override
    public PostMediaDto add(PostMediaDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElse(null);
        if (post == null) throw new NoPermissionException();
        PostMedia e = postMediaMapper.toEntity(dto, post);
        e = postMediaRepository.save(e);
        return postMediaMapper.toDto(e);
    }

    @Override
    public List<PostMediaDto> findByPostId(Integer postId) {
        return postMediaRepository.findByPostId(postId)
                .stream().map(postMediaMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        postMediaRepository.deleteById(id);
    }
}
