package com.meetup.server.place.presentation;

import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.place.application.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Tag(name = "Place API", description = "장소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "모임 장소 확정 및 변경 API", description = "모임 ID와 장소 ID를 통해 모임 장소를 확정/변경합니다.")
    @PostMapping("/{placeId}")
    public ApiResponse<?> confirmPlace(
            @PathVariable UUID placeId,
            @RequestParam UUID eventId
    ) {
        placeService.confirmPlace(eventId, placeId);
        return ApiResponse.success();
    }
}
