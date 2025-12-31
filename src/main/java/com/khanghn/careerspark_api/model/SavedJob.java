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
@IdClass(SavedJobId.class)
@Table(name = "saved_jobs")
public class SavedJob {
    @Id
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;

    @Id
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "saved_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant savedAt = Instant.now();
}

@Data
class SavedJobId implements Serializable {
    private UUID candidate;
    private UUID job;
}
