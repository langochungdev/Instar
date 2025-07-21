package com.instar.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String deviceToken;

    @Column(nullable = false, length = 100)
    private String deviceName;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private Boolean isActive = true;
}
