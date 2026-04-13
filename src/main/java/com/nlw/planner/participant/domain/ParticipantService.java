package com.nlw.planner.participant.domain;

import com.nlw.planner.participant.api.dto.ParticipantSummaryResponse;
import com.nlw.planner.participant.infra.repository.ParticipantRepository;
import com.nlw.planner.participant.api.dto.ParticipantResponse;

import com.nlw.planner.trip.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repository;

    public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.repository.saveAll(participants);

        System.out.println(participants.getFirst().getId());
    }

    public ParticipantSummaryResponse registerParticipantToTrip(String email, Trip trip) {
        Participant participant = new Participant(email, trip);
        this.repository.save(participant);

        return new ParticipantSummaryResponse(participant.getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {

    }

    public void triggerConfirmationEmailToParticipant(String email) {

    }

    public List<ParticipantResponse> getAllParticipantsFromTrip(UUID id) {
        return this.repository.findByTripId(id).stream().map(
                participant ->
                        new ParticipantResponse(
                                participant.getId(),
                                participant.getName(),
                                participant.getEmail(),
                                participant.getIsConfirmed())).toList();
    }
}
