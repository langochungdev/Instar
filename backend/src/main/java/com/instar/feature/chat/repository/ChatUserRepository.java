package com.instar.feature.chat.repository;

import com.instar.feature.chat.entity.ChatUser;
import com.instar.feature.chat.entity.ChatUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {
    List<ChatUser> findByUserId(Integer userId);
    List<ChatUser> findByChatId(Integer chatId);
}
