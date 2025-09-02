package com.instar.feature.chat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "message_attachments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class MessageAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_type", length = 50)
    private String fileType;
}
