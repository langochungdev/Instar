package com.instar.feature.post.model.postMedia;

import java.util.List;

public interface PostMediaService {
    PostMediaDto add(PostMediaDto dto);
    List<PostMediaDto> findByPostId(Integer postId);
    void delete(Integer id);
}
