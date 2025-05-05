package com.meetup.server.event.presentation;

import com.meetup.server.event.application.RouteService;
import com.meetup.server.event.dto.request.RouteRequest;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.global.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Route API", description = "경로 API")
@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("")
    public ApiResponse<RouteResponseList> searchMiddlePoint(RouteRequest routeRequest) {
        RouteResponseList response = routeService.getAllRouteDetails(
                routeRequest.eventId(),
                routeRequest.id()
        );
        return ApiResponse.success(response);
    }
}
