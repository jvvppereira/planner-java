package com.nlw.planner.link.api;

import com.nlw.planner.link.domain.Link;

import java.util.UUID;

public record LinkData(UUID id, String title, String url) {
}
