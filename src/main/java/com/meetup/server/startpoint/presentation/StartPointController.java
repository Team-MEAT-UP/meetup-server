package com.meetup.server.startpoint.presentation;

import com.meetup.server.global.clients.kakao.local.KakaoLocalResponse;
import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.startpoint.application.StartPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "StartPoint", description = "장소 검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/start-point")
public class StartPointController {

    private final StartPointService startPointService;

    @Operation(summary = "장소 검색하기", description = "외부 API를 통해 장소를 검색합니다.")
    @GetMapping("")
    public ApiResponse<KakaoLocalResponse> searchStartPoint(String textQuery) {
        KakaoLocalResponse response = startPointService.searchStartPoint(textQuery);
        return ApiResponse.success(response);
    }
}
