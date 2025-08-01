package com.instar.service.impl;
import com.instar.dto.ChatDto;
import com.instar.entity.Chat;
import com.instar.entity.ChatUser;
import com.instar.entity.User;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.ChatMapper;
import com.instar.repository.ChatRepository;
import com.instar.repository.ChatUserRepository;
import com.instar.repository.UserRepository;
import com.instar.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final ChatUserRepository chatUserRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Override
    public ChatDto createGroup(String chatName, Integer creatorId, List<Integer> memberIds) {
        User creator = userRepository.findById(creatorId).orElse(null);
        if (creator == null) throw new NoPermissionException();

        Chat chat = Chat.builder()
                .chatName(chatName)
                .isGroup(true)
                .createdBy(creator)
                .createdAt(LocalDateTime.now())
                .build();
        chat = chatRepository.save(chat);

        ChatUser adminUser = ChatUser.builder().chat(chat).user(creator).isAdmin(true).build();
        chatUserRepository.save(adminUser);

        for (Integer userId : memberIds) {
            if (!userId.equals(creatorId)) {
                User member = userRepository.findById(userId).orElse(null);
                if (member != null) {
                    ChatUser chatUser = ChatUser.builder().chat(chat).user(member).isAdmin(false).build();
                    chatUserRepository.save(chatUser);
                }
            }
        }
        return chatMapper.toDto(chat);
    }

    @Override
    public List<ChatDto> getUserChats(Integer userId) {
        List<ChatUser> chatUsers = chatUserRepository.findByUserId(userId);
        return chatUsers.stream()
                .map(chatUser -> chatMapper.toDto(chatUser.getChat()))
                .collect(Collectors.toList());
    }

    @Override
    public void addUserToGroup(Integer chatId, Integer userId, boolean isAdmin) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (chat == null || user == null) throw new NoPermissionException();
        ChatUser chatUser = ChatUser.builder().chat(chat).user(user).isAdmin(isAdmin).build();
        chatUserRepository.save(chatUser);
    }
}
