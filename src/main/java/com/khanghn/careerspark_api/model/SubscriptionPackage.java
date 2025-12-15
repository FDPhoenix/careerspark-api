package com.khanghn.careerspark_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription_packages")
public class SubscriptionPackage {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "job_post_limit", nullable = false)
    private Integer jobPostLimit;

    @Column(name = "featured_job_limit", nullable = false)
    private Integer featuredJobLimit;

    @Column(name = "job_post_duration", nullable = false)
    private Integer jobPostDuration;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(name = "description")
    private String description;

    @Builder.Default
    private Instant createdAt =  Instant.now();

    @OneToMany(mappedBy = "subscriptionPackage")
    private Set<UserSubscription> subscriptions = new HashSet<>();
}
