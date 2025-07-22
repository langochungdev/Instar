package com.instar.repository;

import com.instar.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    boolean existsByPostIdAndUserId(Integer postId, Integer userId);
    Optional<Like> findByPostIdAndUserId(Integer postId, Integer userId);
}
