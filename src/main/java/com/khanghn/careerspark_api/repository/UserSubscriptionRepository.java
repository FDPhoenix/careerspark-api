package com.khanghn.careerspark_api.repository;

import com.khanghn.careerspark_api.model.UserSubscription;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<@NonNull UserSubscription, @NonNull UUID> {
    boolean existsBySubscriptionPackageId(UUID id);
}
