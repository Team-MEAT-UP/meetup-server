package com.meetup.server.event.presentation;

import com.meetup.server.event.application.EventService;
import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Event API", description = "모임 API")
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "모임 생성 API", description = "모임 생성자의 출발지를 입력받아 모임을 생성합니다")
    @PostMapping
    public ApiResponse<UUID> createEvent(@Valid @RequestBody StartPointRequest startPointRequest, @AuthenticationPrincipal Long userId) {
        UUID eventId = eventService.createEvent(userId, startPointRequest);
        return ApiResponse.success(eventId);
    }
}
