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
@Table(name = "companies")
public class Company {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    private String website;
    private String description;
    private String logoUrl;
    private Integer foundedYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "is_verified")
    private boolean isVerified = false;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt =  Instant.now();

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    private Set<Job> jobs = new HashSet<>();

    public enum Size {
        SMALL, MEDIUM, LARGE, XLARGE, HUGE, GIANT
    }
}
