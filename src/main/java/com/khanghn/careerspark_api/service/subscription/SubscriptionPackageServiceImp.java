package com.khanghn.careerspark_api.service.subscription;

import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageRequest;
import com.khanghn.careerspark_api.dto.request.subscription.SubscriptionPackageUpdateRequest;
import com.khanghn.careerspark_api.dto.response.subscription.SubscriptionPackageResponse;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.mapper.SubscriptionPackageMapper;
import com.khanghn.careerspark_api.model.SubscriptionPackage;
import com.khanghn.careerspark_api.repository.SubscriptionPackageRepository;
import com.khanghn.careerspark_api.repository.UserSubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionPackageServiceImp implements SubscriptionPackageService{
    private final SubscriptionPackageRepository subscriptionPackageRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionPackageMapper subscriptionPackageMapper;

    @Override
    public List<SubscriptionPackageResponse> getAllSubscriptionPackages() {
        List<SubscriptionPackage> subscriptionPackages = subscriptionPackageRepository.findAll();

        return subscriptionPackageMapper.subscriptionPackageListToSubscriptionPackageResponseList(subscriptionPackages);
    }

    @Override
    public SubscriptionPackageResponse createSubscriptionPackage(SubscriptionPackageRequest req) {
        if (subscriptionPackageRepository.existsByNameIgnoreCase(req.name().trim())) {
            throw new BadRequestException("Package's name already exists!");
        }

        SubscriptionPackage newSubscriptionPackage = subscriptionPackageMapper.subscriptionPackageRequestToSubscriptionPackage(req);
        subscriptionPackageRepository.save(newSubscriptionPackage);

        return subscriptionPackageMapper.subscriptionPackageToSubscriptionPackageResponse(newSubscriptionPackage);
    }

    @Override
    public SubscriptionPackageResponse updateSubscriptionPackage(UUID id, SubscriptionPackageUpdateRequest req) {
        SubscriptionPackage existingPackage = subscriptionPackageRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscription package with id " + id + " not found!"));

        if (req.name() != null && !req.name().trim().isEmpty()) {
            String newName = req.name().trim();
            if (!existingPackage.getName().equalsIgnoreCase(newName)) {
                if (subscriptionPackageRepository.existsByNameIgnoreCase(newName)) {
                    throw new IllegalArgumentException("Package's name already exists!");
                }
            }
        }

        subscriptionPackageMapper.updateFromRequest(req, existingPackage);
        SubscriptionPackage updatedPackage = subscriptionPackageRepository.save(existingPackage);

        return subscriptionPackageMapper.subscriptionPackageToSubscriptionPackageResponse(updatedPackage);
    }

    @Override
    public void deleteSubscriptionPackage(UUID id) {
        SubscriptionPackage pkg = subscriptionPackageRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscription package with id " + id + " not found!"));

        boolean isInUse = userSubscriptionRepository.existsBySubscriptionPackageId(id);

        if (isInUse) {
            throw new IllegalStateException("Subscription package with id " + id + " is already in use!");
        }

        subscriptionPackageRepository.delete(pkg);
    }
}
