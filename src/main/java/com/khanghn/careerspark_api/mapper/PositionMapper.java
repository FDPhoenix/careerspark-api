package com.khanghn.careerspark_api.mapper;

import com.khanghn.careerspark_api.dto.request.position.PositionRequest;
import com.khanghn.careerspark_api.dto.request.position.PositionUpdateRequest;
import com.khanghn.careerspark_api.dto.response.position.PositionResponse;
import com.khanghn.careerspark_api.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PositionMapper {
    Position positionRequestToPosition(PositionRequest positionRequest);
    PositionResponse positionToPositionResponse(Position position);
    List<PositionResponse> positionListToPositionResponseList(List<Position> positions);
    void updateFromRequest(PositionUpdateRequest req, @MappingTarget Position entity);
}
