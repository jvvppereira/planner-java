package com.nlw.planner.activity.domain;

import com.nlw.planner.activity.api.dto.ActivityResponse;
import com.nlw.planner.activity.api.dto.ActivitySummaryResponse;
import com.nlw.planner.activity.infra.repository.ActivityRepository;
import com.nlw.planner.activity.api.dto.CreateActivityRequest;

import com.nlw.planner.trip.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivitySummaryResponse registerActivity(CreateActivityRequest payload, Trip trip) {
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);

        this.repository.save(newActivity);

        return new ActivitySummaryResponse(newActivity.getId());
    }

    public List<ActivityResponse> getAllActivitiesFromTrip(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(activity -> new ActivityResponse(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
