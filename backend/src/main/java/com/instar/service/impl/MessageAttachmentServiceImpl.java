package com.instar.service.impl;

import com.instar.dto.MessageAttachmentDto;
import com.instar.entity.Message;
import com.instar.entity.MessageAttachment;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.MessageAttachmentMapper;
import com.instar.repository.MessageAttachmentRepository;
import com.instar.repository.MessageRepository;
import com.instar.service.MessageAttachmentService;
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
