package com.nlw.planner.link.domain;

import com.nlw.planner.link.infra.repository.LinkRepository;
import com.nlw.planner.link.api.dto.LinkResponse;
import com.nlw.planner.link.api.LinkMapper;
import com.nlw.planner.link.api.dto.CreateLinkRequest;

import com.nlw.planner.trip.domain.Trip;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    @Mock
    private LinkRepository repository;

    @Mock
    private LinkMapper linkMapper;

    @InjectMocks
    private LinkService linkService;

    @Test
    void testRegisterLink() {
        Trip trip = new Trip();
        CreateLinkRequest payload = new CreateLinkRequest("Title", "http://url.com");

        when(linkMapper.toResponse(any(Link.class))).thenReturn(new LinkResponse(UUID.randomUUID(), "Title", "http://url.com"));
        LinkResponse response = linkService.registerLink(payload, trip);

        assertNotNull(response);
        assertEquals("Title", response.title());
        assertEquals("http://url.com", response.url());
        verify(repository, times(1)).save(any(Link.class));
    }

    @Test
    void testGetAllLinksFromTrip() {
        UUID tripId = UUID.randomUUID();
        Link link = new Link("Title", "http://url.com", new Trip());
        link.setId(UUID.randomUUID());

        when(repository.findByTripId(tripId)).thenReturn(List.of(link));
        when(linkMapper.toResponse(any(Link.class))).thenReturn(new LinkResponse(link.getId(), "Title", "http://url.com"));

        List<LinkResponse> result = linkService.getAllLinksFromTrip(tripId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Title", result.getFirst().title());
    }
}
