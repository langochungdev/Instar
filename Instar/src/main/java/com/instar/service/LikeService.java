package com.instar.service;

import com.instar.dto.LikeDto;
import java.util.List;

public interface LikeService {
    LikeDto likePost(Integer postId, Integer userId);
    void unlikePost(Integer postId, Integer userId);
    List<LikeDto> findByPostId(Integer postId);
    boolean isUserLikedPost(Integer postId, Integer userId);
    LikeDto like(Integer postId, Integer userId);
    void unlike(Integer postId, Integer userId);

}
