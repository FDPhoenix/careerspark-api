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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.base-path}")
public class SectorController {
    private final SectorService sectorService;
    private final SectorMapper sectorMapper;

    @GetMapping("/sectors")
    @PreAuthorize("""
       hasRole("ADMIN") || (#available == true)
    """)
    public ResponseObject<List<SectorResponse>> getSectors(
            @RequestParam(value = "available", required = false) Boolean available,
            @RequestParam(value = "popular", required = false) Boolean popular
    ) {
        List<Sector> allSectors = sectorService.getSectors(available, popular);
        return ResponseObject.success(
                "Fetch sectors successfully",
                sectorMapper.sectorListToSectorResponseList(allSectors)
        );
    }

    @PostMapping("/sectors")
    @SecurityRequirement(name = "bearer")
    public ResponseObject<SectorResponse> createSector(@Valid @RequestBody SectorRequest req) {
        Sector newSector = sectorService.createSector(req);
        return ResponseObject.success(
                "Create sector successfully",
                sectorMapper.sectorToSectorResponse(newSector)
        );
    }

    @PutMapping("/sectors")
    @SecurityRequirement(name = "bearer")
    public ResponseObject<SectorResponse> updateSector(@RequestParam String sectorId, @Valid @RequestBody SectorUpdateRequest req) {
        Sector updatedSector = sectorService.updateSector(UUID.fromString(sectorId), req);
        return ResponseObject.success(
                "Update sector successfully",
                sectorMapper.sectorToSectorResponse(updatedSector)
        );
    }
}
