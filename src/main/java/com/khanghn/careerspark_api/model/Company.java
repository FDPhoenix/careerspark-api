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

    @Column(name = "size", columnDefinition = "TEXT CHECK (size IN ('1-10','11-50','51-200','201-500','501-1000','1000+'))")
    private String size;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "is_verified")
    private boolean isVerified = false;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt = Instant.now();
    private Instant updatedAt;

    @OneToMany(mappedBy = "company")
    private Set<Job> jobs = new HashSet<>();
}
