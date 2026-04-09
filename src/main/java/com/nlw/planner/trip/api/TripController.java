package com.nlw.planner.trip.api;

import com.nlw.planner.participant.api.ParticipantCreateResponse;
import com.nlw.planner.participant.api.ParticipantData;
import com.nlw.planner.trip.domain.TripService;
import com.nlw.planner.participant.domain.Participant;
import com.nlw.planner.participant.domain.ParticipantService;
import com.nlw.planner.participant.api.ParticipantRequestPayload;
import com.nlw.planner.link.domain.Link;
import com.nlw.planner.trip.domain.Trip;
import com.nlw.planner.activity.domain.Activity;

import com.nlw.planner.activity.api.ActivityData;
import com.nlw.planner.activity.api.ActivityRequestPayload;
import com.nlw.planner.activity.api.ActivityResponse;
import com.nlw.planner.activity.domain.ActivityService;
import com.nlw.planner.link.api.LinkData;
import com.nlw.planner.link.api.LinkRequestPayload;
import com.nlw.planner.link.domain.LinkService;
import com.nlw.planner.participant.api.*; import com.nlw.planner.participant.domain.*; import com.nlw.planner.participant.infra.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private TripService tripService;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip = this.tripService.createTrip(payload);

        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateDetails(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setDestination(payload.destination());

            this.tripService.saveTrip(rawTrip);

            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);

            this.tripService.saveTrip(rawTrip);
            this.participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            String email = payload.email();

            ParticipantCreateResponse participantResponse = this.participantService.registerParticipantToTrip(email, rawTrip);

            if (rawTrip.getIsConfirmed()) {
                this.participantService.triggerConfirmationEmailToParticipant(email);
            }

            return ResponseEntity.ok(participantResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantData> participantList = this.participantService.getAllParticipantsFromTrip(id);
        return ResponseEntity.ok(participantList);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ActivityResponse activityResponse = this.activityService.registerActivity(payload, rawTrip);

            return ResponseEntity.ok(activityResponse);
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id) {
        List<ActivityData> activitiesList = this.activityService.getAllActivitiesFromTrip(id);
        return ResponseEntity.ok(activitiesList);
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkData> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            LinkData activityResponse = this.linkService.registerLink(payload, rawTrip);

            return ResponseEntity.ok(activityResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id) {
        List<LinkData> linksList = this.linkService.getAllLinksFromTrip(id);
        return ResponseEntity.ok(linksList);
    }

}
