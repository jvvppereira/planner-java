package com.nlw.planner.activity.domain;

import com.nlw.planner.activity.api.ActivityData;
import com.nlw.planner.activity.api.ActivityResponse;
import com.nlw.planner.activity.infra.ActivityRepository;
import com.nlw.planner.activity.api.ActivityRequestPayload;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository repository;

    @InjectMocks
    private ActivityService activityService;

    @Test
    void testRegisterActivity() {
        Trip trip = new Trip();
        ActivityRequestPayload payload = new ActivityRequestPayload("Title", "2026-04-10T10:00:00");

        ActivityResponse response = activityService.registerActivity(payload, trip);

        assertNotNull(response);
        verify(repository, times(1)).save(any(Activity.class));
    }

    @Test
    void testGetAllActivitiesFromTrip() {
        UUID tripId = UUID.randomUUID();
        Activity activity = new Activity("Title", "2026-04-10T10:00:00", new Trip());
        activity.setId(UUID.randomUUID());

        when(repository.findByTripId(tripId)).thenReturn(Arrays.asList(activity));

        List<ActivityData> result = activityService.getAllActivitiesFromTrip(tripId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).title());
    }
}
