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
    public List<Sector> getAllSector() {
        return sectorRepository.findAll();
    }

    @Override
    public List<Sector> getSectors(String type) {
        return type.equals("popular") ?
                sectorRepository.findByIsAvailable(true)
                        .stream()
                        .sorted(Comparator.comparingLong(Sector::getJobNumber).reversed())
                        .limit(8)
                        .toList()
                :
                sectorRepository.findByIsAvailable(true);
    }

    @Override
    public boolean existsBySectorId(UUID sectorId) {
        return !sectorRepository.existsById(sectorId);
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
