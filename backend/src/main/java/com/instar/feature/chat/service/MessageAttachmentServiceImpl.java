package com.instar.feature.chat.service;

import com.instar.common.exception.NoPermissionException;
import com.instar.feature.chat.dto.MessageAttachmentDto;
import com.instar.feature.chat.entity.Message;
import com.instar.feature.chat.entity.MessageAttachment;
import com.instar.feature.chat.mapper.MessageAttachmentMapper;
import com.instar.feature.chat.repository.MessageAttachmentRepository;
import com.instar.feature.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageAttachmentServiceImpl implements MessageAttachmentService {
    private final MessageAttachmentRepository messageAttachmentRepository;
    private final MessageRepository messageRepository;
    private final MessageAttachmentMapper messageAttachmentMapper;

    @Override
    public MessageAttachmentDto add(MessageAttachmentDto dto) {
        Message message = messageRepository.findById(dto.getMessageId()).orElse(null);
        if (message == null) throw new NoPermissionException();
        MessageAttachment e = messageAttachmentMapper.toEntity(dto, message);
        e = messageAttachmentRepository.save(e);
        return messageAttachmentMapper.toDto(e);
    }

    @Override
    public List<MessageAttachmentDto> findByMessageId(Integer messageId) {
        return messageAttachmentRepository.findByMessageId(messageId)
                .stream().map(messageAttachmentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        messageAttachmentRepository.deleteById(id);
    }
}
