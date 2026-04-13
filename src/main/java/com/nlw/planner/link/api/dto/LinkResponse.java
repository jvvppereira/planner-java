package com.nlw.planner.link.api.dto;

import java.util.UUID;

public record LinkResponse(UUID id, String title, String url) {
}
