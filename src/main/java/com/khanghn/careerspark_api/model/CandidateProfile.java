package com.khanghn.careerspark_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate_profiles")
public class CandidateProfile {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String summary;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private Integer salaryMin;
    private Integer salaryMax;

    @Column(name = "years_of_exp")
    private Integer yearsOfExperience = 0;

    @Column(name = "is_open_to_work")
    private boolean isOpenToWork = true;
    private String resumeUrl;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt = Instant.now();
    private Instant updatedAt;
}
