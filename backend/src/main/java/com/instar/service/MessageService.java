package com.instar.service;
import com.instar.dto.MessageDto;
import java.util.List;

public interface MessageService {
    List<MessageDto> getConversations(Integer userId);
    MessageDto send(MessageDto dto);
    void markRead(Integer messageId);
}
