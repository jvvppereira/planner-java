package com.nlw.planner.participant.domain;

import com.nlw.planner.participant.api.ParticipantCreateResponse;
import com.nlw.planner.participant.infra.ParticipantRepository;
import com.nlw.planner.participant.api.ParticipantData;

import com.nlw.planner.trip.domain.Trip;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepository repository;

    @InjectMocks
    private ParticipantService participantService;

    @Test
    void testRegisterParticipantsToTrip() {
        Trip trip = new Trip();
        List<String> emails = Arrays.asList("test@test.com");

        participantService.registerParticipantsToTrip(emails, trip);

        verify(repository, times(1)).saveAll(anyList());
    }

    @Test
    void testRegisterParticipantToTrip() {
        Trip trip = new Trip();
        String email = "test@test.com";

        ParticipantCreateResponse response = participantService.registerParticipantToTrip(email, trip);

        assertNotNull(response);
        verify(repository, times(1)).save(any(Participant.class));
    }

    @Test
    void testGetAllParticipantsFromTrip() {
        UUID tripId = UUID.randomUUID();
        when(repository.findByTripId(tripId)).thenReturn(Arrays.asList(new Participant("test@test.com", new Trip())));

        List<ParticipantData> result = participantService.getAllParticipantsFromTrip(tripId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
