package com.nlw.planner.link.domain;

import com.nlw.planner.link.infra.LinkRepository;
import com.nlw.planner.link.api.LinkResponse;
import com.nlw.planner.link.api.CreateLinkRequest;

import com.nlw.planner.trip.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;

    public LinkResponse registerLink(CreateLinkRequest payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.url(), trip);

        this.repository.save(newLink);

        return new LinkResponse(newLink.getId(), newLink.getTitle(), newLink.getUrl());
    }

    public List<LinkResponse> getAllLinksFromTrip(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(
                link -> new LinkResponse(link.getId(), link.getTitle(), link.getUrl())).toList();
    }
}
