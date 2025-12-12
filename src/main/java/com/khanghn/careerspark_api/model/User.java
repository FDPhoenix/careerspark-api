package com.khanghn.careerspark_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "email", unique = true, nullable = false, columnDefinition = "CITEXT")
    private String email;

    @EqualsAndHashCode.Include
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String fullName;
    private String phone;
    private String avatarUrl;

    @Builder.Default
    private boolean isVerified = false;

    @Builder.Default
    private boolean isActive = true;

    @Builder.Default
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt = Instant.now();

    @Builder.Default
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedAt = Instant.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CandidateProfile candidateProfile;

    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL)
    private Set<Job> postedJobs = new HashSet<>();

    public enum Role { CANDIDATE, RECRUITER, ADMIN }
}
