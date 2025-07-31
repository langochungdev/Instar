package com.instar.repository;
import com.instar.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByChatIdOrderByCreatedAtAsc(Integer chatId);
}
