package com.nlw.planner.participant.api.dto;

import java.util.UUID;

public record ParticipantResponse(UUID id, String name, String email, Boolean isConfirmed) {
}
