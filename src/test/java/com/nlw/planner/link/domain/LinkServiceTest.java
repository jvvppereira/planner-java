package com.nlw.planner.link.domain;

import com.nlw.planner.link.infra.LinkRepository;
import com.nlw.planner.link.api.LinkData;
import com.nlw.planner.link.api.LinkRequestPayload;

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
class LinkServiceTest {

    @Mock
    private LinkRepository repository;

    @InjectMocks
    private LinkService linkService;

    @Test
    void testRegisterLink() {
        Trip trip = new Trip();
        LinkRequestPayload payload = new LinkRequestPayload("Title", "http://url.com");

        LinkData response = linkService.registerLink(payload, trip);

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

        when(repository.findByTripId(tripId)).thenReturn(Arrays.asList(link));

        List<LinkData> result = linkService.getAllLinksFromTrip(tripId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).title());
    }
}
