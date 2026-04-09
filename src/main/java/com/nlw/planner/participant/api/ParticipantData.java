package com.nlw.planner.participant.api;

import com.nlw.planner.participant.domain.Participant;

import java.util.UUID;

public record ParticipantData(UUID id, String name, String email, Boolean isConfirmed) {
}
