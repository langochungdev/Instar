package com.instar.feature.chat.repository;

import com.instar.feature.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByChatIdOrderByCreatedAtAsc(Integer chatId);
}
