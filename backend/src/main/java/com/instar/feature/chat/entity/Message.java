package com.instar.feature.chat.entity;

import com.instar.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "messages", schema = "dbo")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(length = 500)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "datetime2 default getdate()")
    private LocalDateTime createdAt;

    @Column(name = "is_read", nullable = false, columnDefinition = "bit default 0")
    private boolean isRead;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageAttachment> attachments;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
}
