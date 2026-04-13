package com.nlw.planner.trip.api;

import com.nlw.planner.trip.api.dto.TripResponse;
import com.nlw.planner.trip.domain.Trip;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TripMapper {
    TripResponse toResponse(Trip trip);
}
