package com.instar.controller;
import com.instar.dto.MessageDto;
import com.instar.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public MessageDto send(@RequestBody MessageDto dto) {
        return messageService.send(dto);
    }

    @GetMapping("/conversations/{userId}")
    public List<MessageDto> getConversations(@PathVariable Integer userId) {
        return messageService.getConversations(userId);
    }

    @PutMapping("/{id}/read")
    public String markRead(@PathVariable Integer id) {
        messageService.markRead(id);
        return "Đã đọc tin nhắn";
    }
}
