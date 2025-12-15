package com.khanghn.careerspark_api.mapper;

import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageRequest;
import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageUpdateRequest;
import com.khanghn.careerspark_api.dto.response.subscription.SubscriptionPackageResponse;
import com.khanghn.careerspark_api.model.SubscriptionPackage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubscriptionPackageMapper {
    SubscriptionPackage subscriptionPackageRequestToSubscriptionPackage(SubscriptionPackageRequest req);
    List<SubscriptionPackageResponse> subscriptionPackageListToSubscriptionPackageResponseList(List<SubscriptionPackage> subscriptionPackages);
    SubscriptionPackageResponse subscriptionPackageToSubscriptionPackageResponse(SubscriptionPackage subscriptionPackage);
    void updateFromRequest(SubscriptionPackageUpdateRequest request, @MappingTarget SubscriptionPackage entity);
}
