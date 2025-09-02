package com.instar.feature.social.repository;
import com.instar.feature.social.entity.SavedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedPostRepository extends JpaRepository<SavedPost, Integer> {
}
