package com.meetup.server.global.clients.google.place.search;

public record GoogleSearchTextResponse(
        Place[] places
) {
    public record Place(
            String id,
            DisplayName displayName,
            Photo[] photos
    ) {
        public record DisplayName(
                String text,
                String languageCode
        ) {
        }
        public record Photo(
                String name,
                Integer widthPx,
                Integer heightPx,
                AuthorAttribution[] authorAttributions,
                String flagContentUri,
                String googleMapsUri
        ) {
            public record AuthorAttribution(
                    String displayName,
                    String uri,
                    String photoUri
            ) {
            }
        }
    }
}
