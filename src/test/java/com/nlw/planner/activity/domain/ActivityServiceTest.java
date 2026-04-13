package com.nlw.planner.activity.domain;

import com.nlw.planner.activity.api.dto.ActivityResponse;
import com.nlw.planner.activity.api.dto.ActivitySummaryResponse;
import com.nlw.planner.activity.api.ActivityMapper;
import com.nlw.planner.activity.infra.repository.ActivityRepository;
import com.nlw.planner.activity.api.dto.CreateActivityRequest;

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

    @Mock
    private ActivityMapper activityMapper;

    @InjectMocks
    private ActivityService activityService;

    @Test
    void testRegisterActivity() {
        Trip trip = new Trip();
        CreateActivityRequest payload = new CreateActivityRequest("Title", "2026-04-10T10:00:00");

        ActivitySummaryResponse response = activityService.registerActivity(payload, trip);

        assertNotNull(response);
        verify(repository, times(1)).save(any(Activity.class));
    }

    @Test
    void testGetAllActivitiesFromTrip() {
        UUID tripId = UUID.randomUUID();
        Activity activity = new Activity("Title", "2026-04-10T10:00:00", new Trip());
        activity.setId(UUID.randomUUID());

        when(repository.findByTripId(tripId)).thenReturn(Arrays.asList(activity));
        when(activityMapper.toResponse(any(Activity.class))).thenReturn(new ActivityResponse(activity.getId(), "Title", null));

        List<ActivityResponse> result = activityService.getAllActivitiesFromTrip(tripId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).title());
    }
}
