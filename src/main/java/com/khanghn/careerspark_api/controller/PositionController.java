package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.position.PositionRequest;
import com.khanghn.careerspark_api.dto.request.position.PositionUpdateRequest;
import com.khanghn.careerspark_api.dto.response.position.PositionResponse;
import com.khanghn.careerspark_api.mapper.PositionMapper;
import com.khanghn.careerspark_api.model.Position;
import com.khanghn.careerspark_api.service.position.PositionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.base-path}")
public class PositionController {
    private final PositionService positionService;
    private final PositionMapper positionMapper;

    @GetMapping("/positions")
    @SecurityRequirement(name = "bearer")
    public ResponseObject<List<PositionResponse>> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();

        return ResponseObject.success(
                "Fetch all positions successfully",
                positionMapper.positionListToPositionResponseList(positions)
        );
    }

    @GetMapping("/sectors/{sectorId}/positions")
    public ResponseObject<List<PositionResponse>> getPositionsBySectorId(@PathVariable String sectorId) {
        List<Position> positions = positionService.getPositionsBySectorId(UUID.fromString(sectorId));

        return ResponseObject.success(
                "Fetch positions for sector " + sectorId + " successfully",
                positionMapper.positionListToPositionResponseList(positions)
        );
    }

    @PostMapping("/positions")
    @SecurityRequirement(name = "bearer")
    public ResponseObject<PositionResponse> createPosition(@Valid @RequestBody PositionRequest req) {
        Position newPosition = positionService.createPosition(req);

        return ResponseObject.success(
                "Create position successfully",
                positionMapper.positionToPositionResponse(newPosition)
        );
    }

    @PutMapping("/positions")
    @SecurityRequirement(name = "bearer")
    public ResponseObject<PositionResponse> updatePosition(@RequestParam String positionId, @RequestBody PositionUpdateRequest req) {
        Position updatePosition = positionService.updatePosition(UUID.fromString(positionId), req);

        return ResponseObject.success(
                "Update position successfully",
                positionMapper.positionToPositionResponse(updatePosition)
        );
    }
}
