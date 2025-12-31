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
    @ToString.Exclude
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String fullName;
    private String phone;
    private String avatarUrl;

    private boolean isVerified = false;
    private boolean isActive = true;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt = Instant.now();

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedAt = Instant.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CandidateProfile candidateProfile;

    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Job> postedJobs = new HashSet<>();

    public enum Role { CANDIDATE, RECRUITER, ADMIN }
}
