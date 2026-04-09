package com.nlw.planner.link.domain;

import com.nlw.planner.link.infra.LinkRepository;
import com.nlw.planner.link.api.LinkDTO;
import com.nlw.planner.link.api.LinkRequestPayload;

import com.nlw.planner.trip.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;

    public LinkDTO registerLink(LinkRequestPayload payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.url(), trip);

        this.repository.save(newLink);

        return new LinkDTO(newLink.getId(), newLink.getTitle(), newLink.getUrl());
    }

    public List<LinkDTO> getAllLinksFromTrip(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(
                link -> new LinkDTO(link.getId(), link.getTitle(), link.getUrl())).toList();
    }
}
