package com.khanghn.careerspark_api.service.position;

import com.khanghn.careerspark_api.dto.request.position.PositionRequest;
import com.khanghn.careerspark_api.dto.request.position.PositionUpdateRequest;
import com.khanghn.careerspark_api.model.Position;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PositionService {
    List<Position> getAllPositions();
    List<Position> getPositionsBySectorId(UUID id);
    Position createPosition(PositionRequest req);
    Position updatePosition(UUID positionId, PositionUpdateRequest req);
}
