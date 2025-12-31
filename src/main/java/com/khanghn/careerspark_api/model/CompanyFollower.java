package com.khanghn.careerspark_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CompanyFollowerId.class)
@Table(name = "company_followers")
public class CompanyFollower {
    @Id
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;

    @Id
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "followed_at")
    private Instant followedAt = Instant.now();
}

@Data
class CompanyFollowerId implements Serializable {
    private UUID candidate;
    private UUID company;
}
