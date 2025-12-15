package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageRequest;
import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageUpdateRequest;
import com.khanghn.careerspark_api.dto.response.subscription.SubscriptionPackageResponse;
import com.khanghn.careerspark_api.service.subscription.SubscriptionPackageService;
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

    @GetMapping("/")
    public ResponseObject<List<SubscriptionPackageResponse>> getAllSubscriptionPackages() {
        return ResponseObject.success(subscriptionPackageService.getAllSubscriptionPackages());
    }

    @PostMapping("/")
    public ResponseObject<SubscriptionPackageResponse> createSubscriptionPackage(@Valid @RequestBody SubscriptionPackageRequest req) {
        return ResponseObject.success(subscriptionPackageService.createSubscriptionPackage(req));
    }

    @PutMapping("/")
    public ResponseObject<SubscriptionPackageResponse>  updateSubscriptionPackage(@RequestParam String id, @RequestBody SubscriptionPackageUpdateRequest req) {
        return ResponseObject.success(subscriptionPackageService.updateSubscriptionPackage(UUID.fromString(id), req));
    }

    @DeleteMapping("/")
    public ResponseObject<String> deleteSubscriptionPackage(@RequestParam String id) {
        subscriptionPackageService.deleteSubscriptionPackage(UUID.fromString(id));
        return ResponseObject.success("Delete subscription package successfully");
    }
}
