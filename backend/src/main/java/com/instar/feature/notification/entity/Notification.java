package com.instar.feature.notification.entity;
import com.instar.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notifications", schema = "dbo")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    // user nhận thông báo
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // loại notify: NEW_COMMENT, NEW_LIKE, NEW_FOLLOW ...
    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(length = 255)
    private String link;

    // [SỬA] thêm target_id
    @Column(name = "target_id", columnDefinition = "uniqueidentifier")
    private UUID targetId;

    // [SỬA] thêm target_type (POST, COMMENT, USER, CHAT...)
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", length = 20)
    private NotificationTargetType targetType;

    @Column(nullable = false, columnDefinition = "bit default 0")
    private Boolean isRead = false;

    @Column(nullable = false, columnDefinition = "datetime2 default getdate()")
    private LocalDateTime createdAt;
}
