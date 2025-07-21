package com.instar.repository;

import com.instar.entity.SavedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedPostRepository extends JpaRepository<SavedPost, Integer> {
    boolean existsByPostIdAndUserId(Integer postId, Integer userId);
    Optional<SavedPost> findByPostIdAndUserId(Integer postId, Integer userId);
}
