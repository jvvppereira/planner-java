package com.nlw.planner.participant.api;

import com.nlw.planner.participant.api.dto.ParticipantResponse;
import com.nlw.planner.participant.domain.Participant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    ParticipantResponse toResponse(Participant participant);
}
