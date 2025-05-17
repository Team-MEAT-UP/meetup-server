package com.meetup.server.global.clients.google.place.photo;

import com.meetup.server.global.clients.google.place.GooglePlaceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GooglePhotoClient {

    private final WebClient googlePlaceWebClient;
    private final GooglePlaceProperties googlePlaceProperties;

    public GooglePhotoResponse sendRequest(GooglePhotoRequest request) {
        return googlePlaceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + request.name() + "/media")
                        .queryParam("key", googlePlaceProperties.secretKey())
                        .queryParamIfPresent("maxHeightPx", Optional.ofNullable(request.maxHeightPx()))
                        .queryParamIfPresent("maxWidthPx", Optional.ofNullable(request.maxWidthPx()))
                        .build()
                )
                .retrieve()
                .bodyToMono(GooglePhotoResponse.class)
                .block();
    }
}
