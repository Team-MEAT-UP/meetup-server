package com.meetup.server.global.clients.google.place.photo;

import lombok.Builder;

@Builder
public record GooglePhotoRequest(
        String name,
        Integer maxHeightPx,
        Integer maxWidthPx
) {
}
