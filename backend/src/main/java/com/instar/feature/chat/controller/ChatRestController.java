package com.instar.feature.chat.controller;

import com.instar.common.util.CurrentUserUtil;
import com.instar.feature.chat.dto.ChatDto;
import com.instar.feature.chat.mapper.ChatMapper;
import com.instar.feature.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final CurrentUserUtil currentUserUtil;

    @GetMapping("/my")
    public ResponseEntity<List<ChatDto>> getMyChats() {
        UUID currentUserId = currentUserUtil.getCurrentUserId();

        List<ChatDto> chats = chatRepository.findByUserIdWithUsers(currentUserId)
                .stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(chats);
    }
}
