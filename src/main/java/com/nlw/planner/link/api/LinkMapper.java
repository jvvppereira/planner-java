package com.nlw.planner.link.api;

import com.nlw.planner.link.api.dto.LinkResponse;
import com.nlw.planner.link.domain.Link;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    LinkResponse toResponse(Link link);
}
