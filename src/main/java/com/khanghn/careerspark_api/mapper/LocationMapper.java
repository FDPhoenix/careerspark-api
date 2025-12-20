package com.khanghn.careerspark_api.mapper;

import com.khanghn.careerspark_api.dto.request.location.LocationRequest;
import com.khanghn.careerspark_api.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationMapper {
    Location locationRequestToLocation(LocationRequest locationRequest);
}
