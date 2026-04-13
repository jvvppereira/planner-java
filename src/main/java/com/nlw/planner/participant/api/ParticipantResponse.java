package com.nlw.planner.participant.api;

import java.util.UUID;

public record ParticipantResponse(UUID id, String name, String email, Boolean isConfirmed) {
}
