package com.khanghn.careerspark_api.service.position;

import com.khanghn.careerspark_api.dto.request.position.PositionRequest;
import com.khanghn.careerspark_api.dto.request.position.PositionUpdateRequest;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.mapper.PositionMapper;
import com.khanghn.careerspark_api.model.Position;
import com.khanghn.careerspark_api.model.Sector;
import com.khanghn.careerspark_api.repository.PositionRepository;
import com.khanghn.careerspark_api.service.sector.SectorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionServiceImp implements PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final SectorService sectorService;

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public List<Position> getPositionsBySectorId(UUID sectorId) {
        return positionRepository.findBySectorId(sectorId);
    }

    @Override
    public Position createPosition(PositionRequest req) {
        if (positionRepository.existsBySlug(req.slug())) throw new BadRequestException("Position slug already exists");
        Sector newSector = sectorService.getSectorById(UUID.fromString(req.sectorId()));

        Position newPosition = positionMapper.positionRequestToPosition(req);
        newPosition.setSector(newSector);
        positionRepository.save(newPosition);

        return newPosition;
    }

    @Override
    public Position updatePosition(UUID positionId, PositionUpdateRequest req) {
        Position updatePosition = positionRepository
                .findById(positionId)
                .orElseThrow(() -> new EntityNotFoundException("Position not found!"));

        if (req.slug() != null && !req.slug().trim().isEmpty()) {
            if (positionRepository.existsBySlug(req.slug())) {
                throw new BadRequestException("Position slug already exists");
            }
        }

        if ((req.slug() != null) && !req.slug().trim().isEmpty()) {
            if (!sectorService.isSectorExist(UUID.fromString(req.sectorId()))) {
                throw new BadRequestException("Sector not found!");
            }
        }
        Sector updateSector = sectorService.getSectorById(UUID.fromString(req.sectorId()));

        positionMapper.updateFromRequest(req, updatePosition);
        updatePosition.setSector(updateSector);
        positionRepository.save(updatePosition);

        return updatePosition;
    }
}
