package com.instar.feature.chat.entity;

import com.instar.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chats", schema = "dbo")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "chat_name", length = 100)
    private String chatName;

    @Column(name = "is_group", nullable = false)
    private boolean isGroup;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "datetime2 default getdate()")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatUser> chatUsers;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
