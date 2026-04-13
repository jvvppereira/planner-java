package com.nlw.planner.activity.api;

import com.nlw.planner.activity.api.dto.ActivityResponse;
import com.nlw.planner.activity.domain.Activity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    ActivityResponse toResponse(Activity activity);
}
