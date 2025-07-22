package com.instar.service;

import com.instar.dto.SavedPostDto;
import java.util.List;

public interface SavedPostService {
    SavedPostDto savePost(Integer userId, Integer postId);
    void unsavePost(Integer userId, Integer postId);
    List<SavedPostDto> findByUserId(Integer userId);
    boolean isPostSaved(Integer userId, Integer postId);
    SavedPostDto save(Integer postId, Integer userId);
    void unsave(Integer postId, Integer userId);
}
