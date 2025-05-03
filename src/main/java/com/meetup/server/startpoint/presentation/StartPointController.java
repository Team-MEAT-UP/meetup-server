package com.meetup.server.startpoint.presentation;

import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.startpoint.application.StartPointService;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "StartPoint API", description = "출발지 API")
@RestController
@RequiredArgsConstructor
public class StartPointController {

    private final StartPointService startPointService;

    @Operation(summary = "출발지 생성 API", description = "모임 참여자의 출발지를 생성합니다")
    @PostMapping("/events/{eventId}/start-points")
    public ApiResponse<EventStartPointResponse> createStartPoint(
            @PathVariable UUID eventId,
            @Valid @RequestBody StartPointRequest startPointRequest,
            @AuthenticationPrincipal Long userId
    ) {
        return ApiResponse.success(startPointService.createStartPoint(eventId, userId, startPointRequest));
    }
}
