package com.nlw.planner.activity.domain;

import com.nlw.planner.activity.api.ActivityDTO;
import com.nlw.planner.activity.api.ActivityResponse;
import com.nlw.planner.activity.infra.ActivityRepository;
import com.nlw.planner.activity.api.ActivityRequestPayload;

import com.nlw.planner.trip.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) {
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);

        this.repository.save(newActivity);

        return new ActivityResponse(newActivity.getId());
    }

    public List<ActivityDTO> getAllActivitiesFromTrip(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(activity -> new ActivityDTO(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
