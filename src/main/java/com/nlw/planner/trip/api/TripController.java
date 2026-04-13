package com.nlw.planner.trip.api;

import com.nlw.planner.participant.api.ParticipantSummaryResponse;
import com.nlw.planner.participant.api.ParticipantResponse;
import com.nlw.planner.participant.api.InviteParticipantRequest;
import com.nlw.planner.trip.domain.TripService;
import com.nlw.planner.participant.domain.ParticipantService;
import com.nlw.planner.trip.domain.Trip;

import com.nlw.planner.activity.api.ActivityResponse;
import com.nlw.planner.activity.api.CreateActivityRequest;
import com.nlw.planner.activity.api.ActivitySummaryResponse;
import com.nlw.planner.activity.domain.ActivityService;
import com.nlw.planner.link.api.LinkResponse;
import com.nlw.planner.link.api.CreateLinkRequest;
import com.nlw.planner.link.domain.LinkService;
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
    public ResponseEntity<TripSummaryResponse> createTrip(@RequestBody CreateTripRequest payload) {
        Trip newTrip = this.tripService.createTrip(payload);

        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripSummaryResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateDetails(@PathVariable UUID id, @RequestBody CreateTripRequest payload) {
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
    public ResponseEntity<ParticipantSummaryResponse> inviteParticipant(@PathVariable UUID id, @RequestBody InviteParticipantRequest payload) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            String email = payload.email();

            ParticipantSummaryResponse participantResponse = this.participantService.registerParticipantToTrip(email, rawTrip);

            if (rawTrip.getIsConfirmed()) {
                this.participantService.triggerConfirmationEmailToParticipant(email);
            }

            return ResponseEntity.ok(participantResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantResponse>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantResponse> participantList = this.participantService.getAllParticipantsFromTrip(id);
        return ResponseEntity.ok(participantList);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivitySummaryResponse> registerActivity(@PathVariable UUID id, @RequestBody CreateActivityRequest payload) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ActivitySummaryResponse activitySummaryResponse = this.activityService.registerActivity(payload, rawTrip);

            return ResponseEntity.ok(activitySummaryResponse);
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityResponse>> getAllActivities(@PathVariable UUID id) {
        List<ActivityResponse> activitiesList = this.activityService.getAllActivitiesFromTrip(id);
        return ResponseEntity.ok(activitiesList);
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody CreateLinkRequest payload) {
        Optional<Trip> trip = this.tripService.getTripDetails(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            LinkResponse activityResponse = this.linkService.registerLink(payload, rawTrip);

            return ResponseEntity.ok(activityResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkResponse>> getAllLinks(@PathVariable UUID id) {
        List<LinkResponse> linksList = this.linkService.getAllLinksFromTrip(id);
        return ResponseEntity.ok(linksList);
    }

}
