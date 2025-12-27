package com.khanghn.careerspark_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "positions")
public class Position {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @Column(name = "n_job")
    private long jobNumber;

    @Column(name = "sort_order")
    private int sortOrder = 0;

    @Column(name = "is_available")
    private boolean available = true;

    @OneToMany
    @JoinColumn(name = "position_id")
    private Set<Job> jobs = new HashSet<>();
}
