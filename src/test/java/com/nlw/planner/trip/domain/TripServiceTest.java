package com.nlw.planner.trip.domain;

import com.nlw.planner.trip.infra.TripRepository;
import com.nlw.planner.trip.api.TripRequestPayload;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository repository;

    @InjectMocks
    private TripService tripService;

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
        trip.setId(UUID.randomUUID());

        when(repository.save(any(Trip.class))).thenReturn(trip);

        Trip createdTrip = tripService.createTrip(payload);

        assertNotNull(createdTrip);
        assertEquals("Florianópolis", createdTrip.getDestination());
        verify(repository, times(1)).save(any(Trip.class));
    }

    @Test
    void testGetTripDetails() {
        UUID id = UUID.randomUUID();
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDestination("Florianópolis");

        when(repository.findById(id)).thenReturn(Optional.of(trip));

        Optional<Trip> result = tripService.getTripDetails(id);

        assertTrue(result.isPresent());
        assertEquals("Florianópolis", result.get().getDestination());
    }

    @Test
    void testSaveTrip() {
        Trip trip = new Trip();
        trip.setDestination("São Paulo");

        when(repository.save(trip)).thenReturn(trip);

        Trip savedTrip = tripService.saveTrip(trip);

        assertNotNull(savedTrip);
        assertEquals("São Paulo", savedTrip.getDestination());
        verify(repository, times(1)).save(trip);
    }
}
