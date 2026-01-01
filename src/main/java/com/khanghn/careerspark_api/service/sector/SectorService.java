package com.khanghn.careerspark_api.service.sector;

import com.khanghn.careerspark_api.dto.request.sector.SectorRequest;
import com.khanghn.careerspark_api.dto.request.sector.SectorUpdateRequest;
import com.khanghn.careerspark_api.model.Sector;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SectorService {
    List<Sector> getAllSector();
    List<Sector> getSectors(String type);
    boolean existsBySectorId(UUID sectorId);
    Sector createSector(SectorRequest req);
    Sector updateSector(UUID sectorId, SectorUpdateRequest req);
}
