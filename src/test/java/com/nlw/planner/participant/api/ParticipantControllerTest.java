package com.nlw.planner.participant.api;

import com.nlw.planner.participant.infra.ParticipantRepository;
import com.nlw.planner.participant.domain.Participant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantControllerTest {

    @InjectMocks
    private ParticipantController participantController;

    @Mock
    private ParticipantRepository repository;

    @Test
    void testConfirmParticipant() {
        UUID id = UUID.randomUUID();
        Participant participant = new Participant();
        participant.setId(id);
        participant.setIsConfirmed(false);

        ParticipantRequestPayload payload = new ParticipantRequestPayload("New Name", "test@test.com");

        when(repository.findById(id)).thenReturn(Optional.of(participant));
        when(repository.save(any(Participant.class))).thenReturn(participant);

        ResponseEntity<Participant> response = participantController.confirmParticipant(id, payload);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().getIsConfirmed());
        assertEquals("New Name", response.getBody().getName());
    }

    @Test
    void testConfirmParticipantNotFound() {
        UUID id = UUID.randomUUID();
        ParticipantRequestPayload payload = new ParticipantRequestPayload("New Name", "test@test.com");

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Participant> response = participantController.confirmParticipant(id, payload);

        assertTrue(response.getStatusCode().is4xxClientError());
    }
}
