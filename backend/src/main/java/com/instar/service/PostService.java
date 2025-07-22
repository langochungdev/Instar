package com.instar.service;

import com.instar.dto.PostDto;
import java.util.List;

public interface PostService {
    PostDto findById(Integer id);
    PostDto create(PostDto postDto);
    PostDto update(Integer id, PostDto postDto);
    void delete(Integer id);
    List<PostDto> findAll();
    List<PostDto> findByUserId(Integer userId);
}
