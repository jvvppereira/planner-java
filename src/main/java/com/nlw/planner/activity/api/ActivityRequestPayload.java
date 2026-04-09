package com.nlw.planner.activity.api;

import com.nlw.planner.activity.domain.Activity;

public record ActivityRequestPayload(String title, String occurs_at) {
}
