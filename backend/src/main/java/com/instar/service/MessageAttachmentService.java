package com.instar.service;

import com.instar.dto.MessageAttachmentDto;
import java.util.List;

public interface MessageAttachmentService {
    MessageAttachmentDto add(MessageAttachmentDto dto);
    List<MessageAttachmentDto> findByMessageId(Integer messageId);
    void delete(Integer id);
}
