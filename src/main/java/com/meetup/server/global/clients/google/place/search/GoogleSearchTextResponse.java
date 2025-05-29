package com.meetup.server.global.clients.google.place.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleSearchTextResponse(
        List<Place> places,
        List<ContextualContent> contextualContents,
        String searchUri
) {
    public record Place(
            String name,
            String id,
            List<String> types,
            String nationalPhoneNumber,
            String internationalPhoneNumber,
            String formattedAddress,
            List<AddressComponent> addressComponents,
            PlusCode plusCode,
            Location location,
            Viewport viewport,
            Double rating,
            String googleMapsUri,
            OpeningHours regularOpeningHours,
            Integer utcOffsetMinutes,
            String adrFormatAddress,
            String businessStatus,
            String priceLevel,
            Integer userRatingCount,
            String iconMaskBaseUri,
            String iconBackgroundColor,
            DisplayName displayName,
            DisplayName primaryTypeDisplayName,
            Boolean takeout,
            Boolean delivery,
            Boolean servesBreakfast,
            OpeningHours currentOpeningHours,
            String primaryType,
            String shortFormattedAddress,
            List<Review> reviews,
            List<Photo> photos,
            Boolean servesDessert,
            Boolean servesCoffee,
            Boolean restroom,
            PaymentOptions paymentOptions,
            List<ContainingPlace> containingPlaces,
            AddressDescriptor addressDescriptor,
            GoogleMapsLinks googleMapsLinks,
            TimeZone timeZone,
            PostalAddress postalAddress
    ) {
    }

    public record AddressComponent(
            String longText,
            String shortText,
            List<String> types,
            String languageCode
    ) {
    }

    public record PlusCode(
            String globalCode,
            String compoundCode
    ) {
    }

    public record Location(
            Double latitude,
            Double longitude
    ) {
    }

    public record Viewport(
            Location low,
            Location high
    ) {
    }

    public record OpeningHours(
            Boolean openNow,
            List<Period> periods,
            List<String> weekdayDescriptions,
            String nextOpenTime
    ) {
    }

    public record Period(
            OpenClose open,
            OpenClose close
    ) {
    }

    public record OpenClose(
            Integer day,
            Integer hour,
            Integer minute,
            Date date
    ) {
    }

    public record Date(
            Integer year,
            Integer month,
            Integer day
    ) {
    }

    public record DisplayName(
            String text,
            String languageCode
    ) {
    }

    public record Review(
            String name,
            String relativePublishTimeDescription,
            Integer rating,
            Text text,
            Text originalText,
            AuthorAttribution authorAttribution,
            String publishTime,
            String flagContentUri,
            String googleMapsUri
    ) {
    }

    public record Text(
            String text,
            String languageCode
    ) {
    }

    public record AuthorAttribution(
            String displayName,
            String uri,
            String photoUri
    ) {
    }

    public record Photo(
            String name,
            Integer widthPx,
            Integer heightPx,
            List<AuthorAttribution> authorAttributions,
            String flagContentUri,
            String googleMapsUri
    ) {
    }

    public record PaymentOptions(
            Boolean acceptsCreditCards,
            Boolean acceptsCashOnly
    ) {
    }

    public record ContainingPlace(
            String name,
            String id
    ) {
    }

    public record AddressDescriptor(
            List<Landmark> landmarks,
            List<Area> areas
    ) {
    }

    public record Landmark(
            String name,
            String placeId,
            DisplayName displayName,
            List<String> types,
            Double straightLineDistanceMeters
    ) {
    }

    public record Area(
            String name,
            String placeId,
            DisplayName displayName,
            String containment
    ) {
    }

    public record GoogleMapsLinks(
            String directionsUri,
            String placeUri,
            String writeAReviewUri,
            String reviewsUri,
            String photosUri
    ) {
    }

    public record TimeZone(
            String id
    ) {
    }

    public record PostalAddress(
            String regionCode,
            String languageCode,
            String administrativeArea,
            String locality,
            List<String> addressLines
    ) {
    }

    public record ContextualContent(
            List<Photo> photos,
            List<Justification> justifications
    ) {
    }

    public record Justification(
            BusinessAvailabilityAttributesJustification businessAvailabilityAttributesJustification
    ) {
    }

    public record BusinessAvailabilityAttributesJustification(
            Boolean takeout
    ) {
    }
}
