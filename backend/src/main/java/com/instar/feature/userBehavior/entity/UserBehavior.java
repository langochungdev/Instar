package com.instar.feature.userBehavior.entity;
import com.instar.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_behaviors", schema = "dbo") // [SỬA] đổi tên table cho khớp SQL
public class UserBehavior {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, columnDefinition = "uniqueidentifier")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // [SỬA] đặt tên cột rõ ràng
    private User user;

    @Column(nullable = false, length = 50)
    private String action; // ví dụ: VIEW_POST, LIKE_POST, FOLLOW_USER, SEND_MESSAGE

    @Column(name = "target_id", nullable = false, columnDefinition = "uniqueidentifier")
    private UUID targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 20) // [SỬA] thêm target_type
    private BehaviorTargetType targetType;

    @Column(name = "created_at", nullable = false, columnDefinition = "datetime2 default getdate()")
    private LocalDateTime createdAt;
}
