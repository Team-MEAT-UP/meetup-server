package com.meetup.server.event.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.event.exception.EventErrorType;
import com.meetup.server.event.exception.EventException;
import com.meetup.server.event.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventReader {

    private final EventRepository eventRepository;
    private final EventValidator eventValidator;
    private final CacheManager cacheManager;

    public Event read(UUID eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(EventErrorType.EVENT_NOT_FOUND));
    }

    public RouteResponseList readEventCache(UUID eventId) {
        Cache cache = cacheManager.getCache("routeDetails");
        Cache.ValueWrapper wrapper = cache.get(eventId);

        eventValidator.validateEventCacheExist(wrapper);
        return (RouteResponseList) wrapper.get();
    }
}
