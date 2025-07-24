package com.instar.service;
import com.instar.dto.MessageDto;
import java.util.List;

public interface MessageService {
    MessageDto sendMessage(MessageDto messageDto);
    List<MessageDto> getConversation(Integer userId1, Integer userId2);
    List<MessageDto> getConversations(Integer userId);
    MessageDto send(MessageDto dto);
    void markRead(Integer messageId);
}
