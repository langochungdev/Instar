package com.instar.repository;

import com.instar.entity.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostMediaRepository extends JpaRepository<PostMedia, Integer> {
    List<PostMedia> findByPostId(Integer postId);
}
