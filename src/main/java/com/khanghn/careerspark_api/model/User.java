package com.khanghn.careerspark_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue
    private UUID userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @EqualsAndHashCode.Include
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fullName;
    private String phone;
    private String avatarUrl;

    @Column(columnDefinition = "DEFAULT false")
    private Boolean isVerified;
    private Instant verifiedAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedAt;
    private Instant lastLoginAt;

    public enum Role {
        SEEKER, EMPLOYER, ADMIN
    }
}
