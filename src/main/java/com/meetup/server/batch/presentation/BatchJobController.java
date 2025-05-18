package com.meetup.server.batch.presentation;

import com.meetup.server.batch.place.job.RecommendPlaceSaveJob;
import com.meetup.server.global.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class BatchJobController {

    private final RecommendPlaceSaveJob recommendPlaceSaveJob;

    @Hidden
    @PostMapping("/recommend-place")
    public ApiResponse<?> runRecommendPlaceSaveJob(@RequestParam int page) {
        recommendPlaceSaveJob.saveRecommendPlaceJobScheduler(page);
        return ApiResponse.success();
    }
}
