package com.instar.feature.savedPost;
import java.util.List;

public interface SavedPostService {
    List<SavedPostDto> findByUserId(Integer userId);
    boolean isPostSaved(Integer userId, Integer postId);
    SavedPostDto save(Integer postId, Integer userId);
    void unsave(Integer postId, Integer userId);
}
