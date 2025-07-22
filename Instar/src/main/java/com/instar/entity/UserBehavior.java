package com.instar.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "userBehaviors")
public class UserBehavior {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User userId;

    @Column(length = 50)
    private String action;

    @Column(nullable = false)
    private Integer targetId;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
