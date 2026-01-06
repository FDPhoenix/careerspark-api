package com.khanghn.careerspark_api.service.sector;

import com.khanghn.careerspark_api.dto.request.sector.SectorRequest;
import com.khanghn.careerspark_api.dto.request.sector.SectorUpdateRequest;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.mapper.SectorMapper;
import com.khanghn.careerspark_api.model.Sector;
import com.khanghn.careerspark_api.repository.SectorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectorServiceImp implements SectorService {
    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;

    @Override
    public List<Sector> getSectors(Boolean available, Boolean popular) {
        if (Boolean.TRUE.equals(popular) && !Boolean.TRUE.equals(available)) {
            throw new BadRequestException("Parameter 'popular' requires 'available=true'");
        }

        if (Boolean.TRUE.equals(available) && Boolean.TRUE.equals(popular)) {
            return sectorRepository.findByIsAvailable(true)
                    .stream()
                    .sorted(Comparator.comparingLong(Sector::getJobNumber).reversed())
                    .limit(8)
                    .toList();
        }

        if (Boolean.TRUE.equals(available)) return sectorRepository.findByIsAvailable(true);

        if (available != null && popular != null) return sectorRepository.findAll();

        throw new BadRequestException("Invalid query parameters");
    }

    @Override
    public boolean isSectorExist(UUID id) {
        return sectorRepository.existsById(id);
    }

    @Override
    public Sector getSectorById(UUID id) {
        return sectorRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sector with id " + id + " not found"));
    }

    @Override
    public Sector createSector(SectorRequest req) {
        if (sectorRepository.existsBySlug(req.slug())) throw new BadRequestException("Sector slug is already exists");

        Sector newSector = sectorMapper.sectorRequestToSector(req);
        sectorRepository.save(newSector);
        return newSector;
    }

    @Override
    public Sector updateSector(UUID sectorId, SectorUpdateRequest req) {
        Sector updateSector = sectorRepository
                .findById(sectorId)
                .orElseThrow(() -> new EntityNotFoundException("Sector not found"));

        if (req.slug() != null && !req.slug().trim().isEmpty()) {
            if (sectorRepository.existsBySlug(req.slug()))
                throw new BadRequestException("Sector slug is already exists");
        }

        sectorMapper.updateFromRequest(req, updateSector);
        sectorRepository.save(updateSector);

        return updateSector;
    }
}
