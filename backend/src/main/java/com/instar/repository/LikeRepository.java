package com.instar.repository;
import com.instar.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    boolean existsByPostId_IdAndUserId_Id(Integer postId, Integer userId);
    Optional<Like> findByPostId_IdAndUserId_Id(Integer postId, Integer userId);
}
