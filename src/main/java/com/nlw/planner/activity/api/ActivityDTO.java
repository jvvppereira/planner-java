package com.nlw.planner.activity.api;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityDTO (UUID id, String title, LocalDateTime occursAt) {
}
