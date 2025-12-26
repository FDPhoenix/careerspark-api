package com.khanghn.careerspark_api.service.subscription;

import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageRequest;
import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageUpdateRequest;
import com.khanghn.careerspark_api.model.SubscriptionPackage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SubscriptionPackageService {
    List<SubscriptionPackage> getSubscriptionPackages();
    SubscriptionPackage createSubscriptionPackage(SubscriptionPackageRequest req);
    SubscriptionPackage updateSubscriptionPackage(UUID id, SubscriptionPackageUpdateRequest req);
    void deleteSubscriptionPackage(UUID id);
}
