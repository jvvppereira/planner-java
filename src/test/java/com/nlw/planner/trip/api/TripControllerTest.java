package com.nlw.planner.trip.api;

import com.nlw.planner.trip.domain.TripService;
import com.nlw.planner.participant.domain.Participant;
import com.nlw.planner.link.domain.Link;
import com.nlw.planner.trip.domain.Trip;
import com.nlw.planner.activity.domain.Activity;

import com.nlw.planner.activity.api.ActivityResponse;
import com.nlw.planner.link.api.LinkData;
import com.nlw.planner.participant.api.ParticipantCreateResponse;
import com.nlw.planner.participant.domain.ParticipantService;
import com.nlw.planner.activity.domain.ActivityService;
import com.nlw.planner.link.domain.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripControllerTest {

    @InjectMocks
    private TripController tripController;

    @Mock
    private TripService tripService;

    @Mock
    private ParticipantService participantService;

    @Mock
    private ActivityService activityService;

    @Mock
    private LinkService linkService;

    @Test
    void testCreateTrip() {
        TripRequestPayload payload = new TripRequestPayload(
                "Florianópolis",
                "2026-04-10T10:00:00",
                "2026-04-20T10:00:00",
                Arrays.asList("email@test.com"),
                "owner@test.com",
                "Owner Name"
        );

        Trip trip = new Trip(payload);
        UUID id = UUID.randomUUID();
        trip.setId(id);

        when(tripService.createTrip(any(TripRequestPayload.class))).thenReturn(trip);

        ResponseEntity<TripCreateResponse> response = tripController.createTrip(payload);

        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
    }

    @Test
    void testGetTripDetails() {
        UUID id = UUID.randomUUID();
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDestination("Florianópolis");

        when(tripService.getTripDetails(id)).thenReturn(Optional.of(trip));

        ResponseEntity<Trip> response = tripController.getTripDetails(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Florianópolis", response.getBody().getDestination());
    }

    @Test
    void testGetTripDetailsNotFound() {
        UUID id = UUID.randomUUID();
        when(tripService.getTripDetails(id)).thenReturn(Optional.empty());

        ResponseEntity<Trip> response = tripController.getTripDetails(id);

        assertTrue(response.getStatusCode().is4xxClientError());
    }
}
