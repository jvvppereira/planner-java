package com.nlw.planner.activity.api;

import com.nlw.planner.activity.domain.Activity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityData(UUID id, String title, LocalDateTime occursAt) {
}
