package com.nlw.planner.trip.infra.repository;

import com.nlw.planner.trip.domain.Trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
