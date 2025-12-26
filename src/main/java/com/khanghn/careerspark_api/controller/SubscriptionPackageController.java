package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageRequest;
import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageUpdateRequest;
import com.khanghn.careerspark_api.dto.response.subscription.SubscriptionPackageResponse;
import com.khanghn.careerspark_api.mapper.SubscriptionPackageMapper;
import com.khanghn.careerspark_api.model.SubscriptionPackage;
import com.khanghn.careerspark_api.service.subscription.SubscriptionPackageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/packages")
public class SubscriptionPackageController {
    private final SubscriptionPackageService subscriptionPackageService;
    private final SubscriptionPackageMapper subscriptionPackageMapper;

    @GetMapping("/")
    public ResponseObject<List<SubscriptionPackageResponse>> getAllSubscriptionPackages() {
        List<SubscriptionPackage> subscriptionPackages = subscriptionPackageService.getSubscriptionPackages();
        return ResponseObject.success(
                "Get subscription packages successfully",
                subscriptionPackageMapper.subscriptionPackageListToSubscriptionPackageResponseList(subscriptionPackages)
        );
    }

    @PostMapping("/")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseObject<SubscriptionPackageResponse> createSubscriptionPackage(@Valid @RequestBody SubscriptionPackageRequest req) {
        SubscriptionPackage newPackage = subscriptionPackageService.createSubscriptionPackage(req);
        return ResponseObject.success(
                "Create subscription package successfully",
                subscriptionPackageMapper.subscriptionPackageToSubscriptionPackageResponse(newPackage)
        );
    }

    @PutMapping("/")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseObject<SubscriptionPackageResponse> updateSubscriptionPackage(@RequestParam String id, @RequestBody SubscriptionPackageUpdateRequest req) {
        SubscriptionPackage updatePackage = subscriptionPackageService.updateSubscriptionPackage(UUID.fromString(id), req);
        return ResponseObject.success(
                "Update subscription package successfully",
                subscriptionPackageMapper.subscriptionPackageToSubscriptionPackageResponse(updatePackage)
        );
    }

    @DeleteMapping("/")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseObject<String> deleteSubscriptionPackage(@RequestParam String id) {
        subscriptionPackageService.deleteSubscriptionPackage(UUID.fromString(id));
        return ResponseObject.success("Delete subscription package successfully");
    }
}
