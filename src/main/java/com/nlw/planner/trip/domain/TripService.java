package com.nlw.planner.trip.domain;

import com.nlw.planner.trip.infra.TripRepository;
import com.nlw.planner.trip.api.TripRequestPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository repository;

    public Trip createTrip(TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);
        return this.repository.save(newTrip);
    }

    public Optional<Trip> getTripDetails(UUID id) {
        return this.repository.findById(id);
    }

    public Trip saveTrip(Trip trip) {
        return this.repository.save(trip);
    }
}
