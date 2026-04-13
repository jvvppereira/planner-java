package com.nlw.planner.link.domain;

import com.nlw.planner.link.infra.repository.LinkRepository;
import com.nlw.planner.link.api.dto.LinkResponse;
import com.nlw.planner.link.api.LinkMapper;
import com.nlw.planner.link.api.dto.CreateLinkRequest;

import com.nlw.planner.trip.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;

    @Autowired
    private LinkMapper linkMapper;

    public LinkResponse registerLink(CreateLinkRequest payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.url(), trip);

        this.repository.save(newLink);

        return this.linkMapper.toResponse(newLink);
    }

    public List<LinkResponse> getAllLinksFromTrip(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(this.linkMapper::toResponse).toList();
    }
}
