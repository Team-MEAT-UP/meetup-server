package com.meetup.server.place.domain.value;

import com.meetup.server.global.clients.google.place.photo.GooglePhotoResponse;
import lombok.Builder;

@Builder
public record Image(
        String name,
        String photoUri
) {
    public static Image from(GooglePhotoResponse googlePhotoResponse) {
        return Image.builder()
                .name(googlePhotoResponse.name())
                .photoUri(googlePhotoResponse.photoUri())
                .build();
    }
}
