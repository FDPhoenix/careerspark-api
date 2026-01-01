package com.khanghn.careerspark_api.service.position;

import com.khanghn.careerspark_api.dto.request.position.PositionRequest;
import com.khanghn.careerspark_api.dto.request.position.PositionUpdateRequest;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.mapper.PositionMapper;
import com.khanghn.careerspark_api.model.Position;
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
        if (sectorService.existsBySectorId(req.sectorId())) throw new EntityNotFoundException("Sector not found!");

        Position newPosition = positionMapper.positionRequestToPosition(req);
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

        if (req.sectorId() != null) {
            if (sectorService.existsBySectorId(req.sectorId())) throw new EntityNotFoundException("Sector not found!");
        }

        positionMapper.updateFromRequest(req, updatePosition);
        positionRepository.save(updatePosition);

        return updatePosition;
    }
}
