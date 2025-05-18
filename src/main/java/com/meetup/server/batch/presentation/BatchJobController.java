package com.meetup.server.batch.presentation;

import com.meetup.server.batch.place.job.RecommendPlaceSaveJob;
import com.meetup.server.global.support.error.GlobalErrorType;
import com.meetup.server.global.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class BatchJobController {

    @Value("${batch.secret-key}")
    private String batchSecretKey;

    private final RecommendPlaceSaveJob recommendPlaceSaveJob;

    @Hidden
    @PostMapping("/recommend-place")
    public ApiResponse<?> runRecommendPlaceSaveJob(@RequestParam int page, @RequestHeader String secretKey) {
        if (!batchSecretKey.equals(secretKey)) {
            return ApiResponse.error(GlobalErrorType.UNAUTHORIZED);
        }

        recommendPlaceSaveJob.saveRecommendPlaceJobScheduler(page);
        return ApiResponse.success();
    }
}
