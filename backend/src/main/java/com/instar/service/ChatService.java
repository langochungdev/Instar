package com.instar.service;

import com.instar.dto.ChatDto;
import java.util.List;

public interface ChatService {
    ChatDto createGroup(String chatName, Integer creatorId, List<Integer> memberIds);
    List<ChatDto> getUserChats(Integer userId);
    void addUserToGroup(Integer chatId, Integer userId, boolean isAdmin);
}
