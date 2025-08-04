package com.instar.feature.like;

import java.util.List;

public interface LikeService {
    List<LikeDto> findByPostId(Integer postId);
    boolean isUserLikedPost(Integer postId, Integer userId);
    LikeDto like(Integer userId, Integer postId);
    void unlike(Integer postId, Integer userId);

}
