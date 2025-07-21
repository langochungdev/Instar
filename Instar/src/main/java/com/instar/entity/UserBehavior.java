package com.instar.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User_Behaviors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBehavior {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String action;

    @Column(nullable = false)
    private Integer targetId;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
