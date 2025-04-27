package com.meetup.server.global.clients.google.place.search;

public record GoogleSearchTextRequest(
        String textQuery
) {
    public static GoogleSearchTextRequest from(String textQuery) {
        return new GoogleSearchTextRequest(textQuery);
    }
}
