package com.instar.controller;

import com.instar.dto.MessageDto;
import com.instar.entity.Chat;
import com.instar.entity.ChatUser;
import com.instar.repository.ChatRepository;
import com.instar.repository.ChatUserRepository;
import com.instar.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatUserRepository chatUserRepository;
    private final MessageService messageService;
    private final ChatRepository chatRepository;

    @MessageMapping("/chat.send") // client gửi tới /app/chat.send
    public void sendMessage(@Payload MessageDto messageDto) {
        // lưu tin nhắn vào DB
        MessageDto saved = messageService.send(messageDto);

        // lấy danh sách thành viên phòng chat
        Chat chat = chatRepository.findById(saved.getChatId()).orElse(null);
        if (chat == null) return;

        List<ChatUser> members = chatUserRepository.findByChatId(chat.getId());
        for (ChatUser cu : members) {
            String dest = "/topic/chat/" + chat.getId() + "/user/" + cu.getUser().getId();
            messagingTemplate.convertAndSend(dest, saved);
        }
    }
}
