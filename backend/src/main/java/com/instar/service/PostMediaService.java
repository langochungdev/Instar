package com.instar.service;

import com.instar.dto.PostMediaDto;
import java.util.List;

public interface PostMediaService {
    PostMediaDto add(PostMediaDto dto);
    List<PostMediaDto> findByPostId(Integer postId);
    void delete(Integer id);
}
