package com.meetup.server.place.presentation;

import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.place.application.PlaceService;
import com.meetup.server.place.dto.response.PlaceDetailResponse;
import com.meetup.server.place.dto.response.PlaceResponseList;
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

    @Operation(summary = "장소 추천 리스트 조회 API", description = "역 주변의 장소 추천 리스트를 조회합니다")
    @GetMapping
    public ApiResponse<PlaceResponseList> getAllPlaces(@RequestParam UUID eventId) {
        return ApiResponse.success(placeService.getAllPlaces(eventId));
    }

    @Operation(summary = "장소 상세 조회 API", description = "장소 ID를 통해 장소의 상세 정보를 조회합니다.")
    @GetMapping("/{placeId}")
    public ApiResponse<PlaceDetailResponse> getPlaceDetails(
            @PathVariable UUID placeId,
            @RequestParam UUID eventId
    ) {
        return ApiResponse.success(placeService.getPlace(eventId, placeId));
    }
}
