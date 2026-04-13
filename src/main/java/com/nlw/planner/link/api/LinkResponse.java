package com.nlw.planner.link.api;

import java.util.UUID;

public record LinkResponse(UUID id, String title, String url) {
}
