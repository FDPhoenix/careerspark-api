package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.sector.SectorRequest;
import com.khanghn.careerspark_api.dto.request.sector.SectorUpdateRequest;
import com.khanghn.careerspark_api.dto.response.sector.SectorResponse;
import com.khanghn.careerspark_api.mapper.SectorMapper;
import com.khanghn.careerspark_api.model.Sector;
import com.khanghn.careerspark_api.service.sector.SectorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sectors")
public class SectorController {
    private final SectorService sectorService;
    private final SectorMapper sectorMapper;

    @GetMapping("/")
    public ResponseObject<List<SectorResponse>> getSectors(@RequestParam(value = "type", required = false, defaultValue = "available") String type) {
        List<Sector> allSectors = sectorService.getSectors(type);
        return ResponseObject.success(sectorMapper.sectorListToSectorResponseList(allSectors));
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseObject<List<SectorResponse>> getAllSector() {
        List<Sector> allSectors = sectorService.getAllSector();
        return ResponseObject.success(sectorMapper.sectorListToSectorResponseList(allSectors));
    }

    @PostMapping("/")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseObject<SectorResponse> createSector(@Valid @RequestBody SectorRequest req) {
        Sector newSector = sectorService.createSector(req);
        return ResponseObject.success(sectorMapper.sectorToSectorResponse(newSector));
    }

    @PutMapping("/")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseObject<SectorResponse> updateSector(@RequestParam String sectorId, @Valid @RequestBody SectorUpdateRequest req) {
        Sector updatedSector = sectorService.updateSector(UUID.fromString(sectorId), req);
        return ResponseObject.success(sectorMapper.sectorToSectorResponse(updatedSector));
    }
}
