package com.instar.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "savedPosts")
public class SavedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post postId;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
