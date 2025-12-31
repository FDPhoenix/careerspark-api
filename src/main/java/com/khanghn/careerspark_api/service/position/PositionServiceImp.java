package com.khanghn.careerspark_api.service.position;

import com.khanghn.careerspark_api.dto.request.position.PositionRequest;
import com.khanghn.careerspark_api.dto.request.position.PositionUpdateRequest;
import com.khanghn.careerspark_api.model.Position;
import com.khanghn.careerspark_api.repository.PositionRepository;
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

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public List<Position> getPositionsBySectorId(UUID id) {
        return positionRepository.findBySectorId(id);
    }

    @Override
    public Position createPosition(PositionRequest req) {
        return null;
    }

    @Override
    public Position updatePosition(UUID positionId, PositionUpdateRequest req) {
        return null;
    }
}
