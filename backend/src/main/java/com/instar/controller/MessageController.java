package com.instar.controller;
import com.instar.dto.MessageAttachmentDto;
import com.instar.dto.MessageDto;
import com.instar.entity.Chat;
import com.instar.entity.ChatUser;
import com.instar.entity.Message;
import com.instar.entity.User;
import com.instar.mapper.MessageAttachmentMapper;
import com.instar.mapper.MessageMapper;
import com.instar.repository.ChatRepository;
import com.instar.repository.ChatUserRepository;
import com.instar.repository.MessageRepository;
import com.instar.repository.UserRepository;
import com.instar.service.MessageAttachmentService;
import com.instar.service.MessageService;
import com.instar.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageAttachmentService messageAttachmentService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatUserRepository chatUserRepository;
    private final MessageRepository messageRepository;
    private final JwtUtil jwtUtil;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${upload.dir:uploads}") // lấy trong file cấu hình, mặc định uploads
    private String uploadDir;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessageWithFiles(
            @RequestParam("chatId") Integer chatId,
            @RequestParam("content") String content,
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request
    ) throws IOException {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token))
            return ResponseEntity.status(401).body("Unauthorized");

        Integer userId = Integer.parseInt(jwtUtil.extractUserId(token));
        User sender = userRepository.findById(userId).orElse(null);
        Chat chat = chatRepository.findById(chatId).orElse(null);

        if (sender == null || chat == null)
            return ResponseEntity.badRequest().body("User hoặc Chat không tồn tại");

        Message message = Message.builder()
                .chat(chat)
                .sender(sender)
                .content(content)
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();
        message = messageRepository.save(message);

        List<MessageAttachmentDto> attachments = new ArrayList<>();
        if (files != null) {
            File uploadPath = new File(System.getProperty("user.dir") + File.separator + uploadDir);
            if (!uploadPath.exists()) uploadPath.mkdirs();
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;
                String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
                String filename = System.currentTimeMillis() + "_" + originalFilename;
                File dest = new File(uploadPath, filename);
                file.transferTo(dest);
                String type = (file.getContentType() != null && file.getContentType().startsWith("video")) ? "video"
                        : (file.getContentType() != null && file.getContentType().startsWith("image")) ? "image"
                        : (file.getContentType() != null && file.getContentType().equals("application/pdf")) ? "pdf"
                        : "other";
                MessageAttachmentDto attachmentDto = MessageAttachmentDto.builder()
                        .messageId(message.getId())
                        .fileUrl(filename)
                        .fileType(type)
                        .build();
                attachments.add(messageAttachmentService.add(attachmentDto));
            }
        }

        MessageDto messageDto = messageMapper.toDto(message);
        messageDto.setAttachments(attachments);

        // Broadcast message cho toàn bộ user trong chat
        List<ChatUser> members = chatUserRepository.findByChatId(chatId);
        for (ChatUser cu : members) {
            String dest = "/topic/chat/" + chatId + "/user/" + cu.getUser().getId();
            messagingTemplate.convertAndSend(dest, messageDto);
        }

        return ResponseEntity.ok(messageDto);
    }

//    @PostMapping
//    public MessageDto send(@RequestBody MessageDto dto) {
//        return messageService.save(dto);
//    }

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
