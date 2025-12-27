package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.response.position.PositionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.base-path}/sectors/{sectorId}/positions")
public class SectorPositionController {
    @GetMapping("/")
    public ResponseObject<List<PositionResponse>> getPositionsBySectorId(@PathVariable String sectorId) {
        return ResponseObject.success(
                "Fetch positions for sector " + sectorId + " successfully",
                null
        );
    }
}
