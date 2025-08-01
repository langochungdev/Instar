package com.instar.service.impl;
import com.instar.dto.MessageDto;
import com.instar.entity.Chat;
import com.instar.entity.Message;
import com.instar.entity.User;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.MessageMapper;
import com.instar.repository.ChatRepository;
import com.instar.repository.MessageRepository;
import com.instar.repository.UserRepository;
import com.instar.service.MessageService;
import com.instar.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto save(MessageDto dto) {
        // Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        // boolean admin = CurrentUserUtil.isAdmin();
        // if (!dto.getSenderId().equals(currentUserId) && !admin) {
        //     throw new NoPermissionException();
        // }
        User sender = userRepository.findById(dto.getSenderId()).orElse(null);
        Chat chat = chatRepository.findById(dto.getChatId()).orElse(null);

        if (sender == null || chat == null) throw new NoPermissionException();

        Message e = Message.builder()
                .chat(chat)
                .sender(sender)
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        e = messageRepository.save(e);
        return messageMapper.toDto(e);
    }

    @Override
    public List<MessageDto> getConversations(Integer chatId) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null) throw new NoPermissionException();
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId)
                .stream().map(messageMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void markRead(Integer messageId) {
        Message e = messageRepository.findById(messageId).orElse(null);
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (e == null) throw new NoPermissionException();
        e.setIsRead(true);
        messageRepository.save(e);
    }
}
