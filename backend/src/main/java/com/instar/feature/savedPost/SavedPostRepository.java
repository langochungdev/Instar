package com.instar.feature.savedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedPostRepository extends JpaRepository<SavedPost, Integer> {
    boolean existsByPostId_IdAndUserId_Id(Integer postId, Integer userId);
    Optional<SavedPost> findByPostId_IdAndUserId_Id(Integer postId, Integer userId);
}
