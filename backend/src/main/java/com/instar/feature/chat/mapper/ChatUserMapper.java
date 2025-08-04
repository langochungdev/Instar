package com.instar.feature.chat.mapper;

import com.instar.feature.chat.dto.ChatUserDto;
import com.instar.feature.chat.entity.Chat;
import com.instar.feature.chat.entity.ChatUser;
import com.instar.feature.user.User;
import org.springframework.stereotype.Component;

@Component
public class ChatUserMapper {
    public ChatUserDto toDto(ChatUser e) {
        return ChatUserDto.builder()
                .chatId(e.getChat().getId())
                .userId(e.getUser().getId())
                .isAdmin(e.getIsAdmin())
                .build();
    }

    public ChatUser toEntity(ChatUserDto dto, Chat chat, User user) {
        return ChatUser.builder()
                .chat(chat)
                .user(user)
                .isAdmin(dto.getIsAdmin())
                .build();
    }
}
