package com.instar.feature.chat.repository;

import com.instar.feature.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query("select c from Chat c " +
            "join c.chatUsers cu1 " +
            "join c.chatUsers cu2 " +
            "where c.isGroup = false " +
            "and cu1.user.id = :userId1 " +
            "and cu2.user.id = :userId2")
    Optional<Chat> findPrivateChat(@Param("userId1") UUID userId1,
                                   @Param("userId2") UUID userId2);

    @Query("select distinct c from Chat c " +
            "join fetch c.chatUsers cu " +
            "where cu.user.id = :userId")
    List<Chat> findByUserIdWithUsers(@Param("userId") UUID userId);
}
