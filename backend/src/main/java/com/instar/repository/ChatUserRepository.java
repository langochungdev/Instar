package com.instar.repository;

import com.instar.entity.ChatUser;
import com.instar.entity.ChatUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {
    List<ChatUser> findByUserId(Integer userId);
    List<ChatUser> findByChatId(Integer chatId);
}
