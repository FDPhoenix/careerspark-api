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
@Table(name = "jobs")
public class Job {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "job_sectors",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    private Set<Sector> sectors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false, length = 10000)
    private String description;

    private String requirements;
    private String benefits;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    private boolean isRemote = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level")
    private ExperienceLevel experienceLevel;

    private Integer yearsOfExpMin;
    private Integer yearsOfExpMax;
    private Integer salaryMin;
    private Integer salaryMax;
    private String salaryCurrency = "VND";
    private boolean isSalaryVisible = true;
    private Instant availableUntil;

    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.ACTIVE;

    @Column(name = "views_count")
    private long viewsCount = 0;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant postedAt = Instant.now();
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name = "job_required_skills",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();

    public enum JobType { FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, REMOTE, HYBRID }
    public enum ExperienceLevel { INTERN, JUNIOR, MIDDLE, SENIOR, LEADER, MANAGER, EXECUTIVE }
    public enum JobStatus { DRAFT, ACTIVE, PAUSED, CLOSED, EXPIRED }
}
