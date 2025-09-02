package com.instar.feature.chat.service;
import com.instar.feature.chat.dto.MessageDto;

public interface ChatService {
    void sendPrivateMessage(MessageDto dto);
    void sendGroupMessage(MessageDto dto);
}
