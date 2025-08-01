package com.instar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_media")
public class PostMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "media_url", length = 255, nullable = false)
    private String mediaUrl;

    @Column(name = "media_type", length = 50, nullable = false)
    private String mediaType; // 'image' hoặc 'video'
}
