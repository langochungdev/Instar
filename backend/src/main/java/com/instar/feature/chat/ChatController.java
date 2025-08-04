package com.instar.feature.chat;

import com.instar.feature.chat.dto.MessageDto;
import com.instar.feature.chat.entity.Chat;
import com.instar.feature.chat.entity.ChatUser;
import com.instar.feature.chat.repository.ChatRepository;
import com.instar.feature.chat.repository.ChatUserRepository;
import com.instar.feature.chat.service.MessageService;
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

    @MessageMapping("/chat.send") // từ prefix app
    public void sendMessage(@Payload MessageDto dto) {
        MessageDto mess = messageService.save(dto);

        Chat chat = chatRepository.findById(mess.getChatId()).orElse(null);
        if (chat == null) return;

        List<ChatUser> members = chatUserRepository.findByChatId(chat.getId());
        for (ChatUser cu : members) {
            //destination điểm đến topic/chat/chat1/user/user1
            String dest = "/topic/chat/" + chat.getId() + "/user/" + cu.getUser().getId();
            messagingTemplate.convertAndSend(dest, mess);
        }
    }
}
