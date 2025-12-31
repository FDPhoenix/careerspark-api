package com.khanghn.careerspark_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sectors")
public class Sector {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    private String iconUrl;
    private String description;

    @Column(name = "n_job")
    private long jobNumber = 0;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "is_available")
    private boolean isAvailable = true;

    @OneToMany
    @JoinColumn(name = "sector_id")
    private Set<Job> jobs = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "sector_id")
    private Set<Position> positions = new HashSet<>();
}
