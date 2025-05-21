package com.meetup.server.place.implement;

import com.meetup.server.place.dto.response.PlaceResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class PlaceSorter {

    public List<PlaceResponse> sortByRating(List<PlaceResponse> placeResponses) {
        List<PlaceResponse> placeResponsesWithReview = placeResponses.stream()
                .filter(placeResponse -> placeResponse.averageRating() != null)
                .sorted(Comparator.comparing(PlaceResponse::averageRating).reversed())
                .toList();

        List<PlaceResponse> placeResponsesWithoutReview = placeResponses.stream()
                .filter(placeResponse -> placeResponse.averageRating() == null)
                .sorted(Comparator.comparing(PlaceResponse::googleRating).reversed())
                .toList();

        List<PlaceResponse> mergedPlaceResponses = new ArrayList<>();
        mergedPlaceResponses.addAll(placeResponsesWithReview);
        mergedPlaceResponses.addAll(placeResponsesWithoutReview);
        return mergedPlaceResponses;
    }
}
