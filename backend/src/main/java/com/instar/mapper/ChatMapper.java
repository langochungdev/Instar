package com.instar.mapper;

import com.instar.dto.ChatDto;
import com.instar.entity.Chat;
import com.instar.entity.ChatUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMapper {
    public ChatDto toDto(Chat chat) {
        ChatDto dto = new ChatDto();
        dto.setId(chat.getId());
        dto.setChatName(chat.getChatName());
        dto.setIsGroup(chat.getIsGroup());
        dto.setCreatedAt(chat.getCreatedAt());
        dto.setCreatedById(chat.getCreatedBy() != null ? chat.getCreatedBy().getId() : null);

        // Lấy danh sách user id từ chatUsers
        List<Integer> memberIds = chat.getChatUsers() != null
                ? chat.getChatUsers().stream()
                .map(ChatUser::getUser)
                .map(user -> user.getId())
                .collect(Collectors.toList())
                : null;
        dto.setMemberIds(memberIds);

        return dto;
    }
}
