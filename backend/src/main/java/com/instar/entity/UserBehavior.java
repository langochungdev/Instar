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


    @Column(nullable = false)
    private LocalDateTime createdAt;
}
