package com.instar.feature.chat.repository;

import com.instar.feature.chat.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatUserRepository extends JpaRepository<ChatUser, UUID> {
}
