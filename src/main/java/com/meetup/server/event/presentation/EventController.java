package com.meetup.server.event.presentation;

import com.meetup.server.event.application.EventService;
import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Event API", description = "모임 API")
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "모임 생성 API", description = "모임 생성자의 출발지를 입력받아 모임을 생성합니다")
    @PostMapping
    public ApiResponse<EventStartPointResponse> createEvent(@Valid @RequestBody StartPointRequest startPointRequest, @AuthenticationPrincipal Long userId) {
        return ApiResponse.success(eventService.createEvent(userId, startPointRequest));
    }

    @Operation(summary = "지도 조회 API", description = "모임의 중간 지점 계산 및 모임 참여자의 경로 조회를 진행합니다")
    @GetMapping("/{eventId}")
    public ApiResponse<RouteResponseList> getEventMap(@PathVariable UUID eventId, @RequestParam(required = false) UUID startPointId) {
        return ApiResponse.success(eventService.getEventMap(eventId, startPointId));
    }
}
