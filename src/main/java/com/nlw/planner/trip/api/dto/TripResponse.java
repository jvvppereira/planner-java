package com.nlw.planner.trip.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TripResponse(
        UUID id,
        String destination,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        Boolean isConfirmed,
        String ownerName,
        String ownerEmail
) {
}
