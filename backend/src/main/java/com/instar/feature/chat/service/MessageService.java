package com.instar.feature.chat.service;


import com.instar.feature.chat.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getConversations(Integer userId);
    MessageDto save(MessageDto dto);
    void markRead(Integer messageId);
}
