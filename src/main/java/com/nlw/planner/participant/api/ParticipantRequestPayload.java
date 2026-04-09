package com.nlw.planner.participant.api;

import com.nlw.planner.participant.domain.Participant;

public record ParticipantRequestPayload(String name, String email) {
}
