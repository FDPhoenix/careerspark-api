package com.khanghn.careerspark_api.mapper;

import com.khanghn.careerspark_api.dto.request.sector.SectorRequest;
import com.khanghn.careerspark_api.dto.request.sector.SectorUpdateRequest;
import com.khanghn.careerspark_api.dto.response.sector.SectorResponse;
import com.khanghn.careerspark_api.model.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SectorMapper {
    SectorResponse sectorToSectorResponse(Sector sector);
    Sector sectorRequestToSector(SectorRequest sectorRequest);
    List<SectorResponse> sectorListToSectorResponseList(List<Sector> sectors);
    void updateFromRequest(SectorUpdateRequest req, @MappingTarget Sector entity);
}
