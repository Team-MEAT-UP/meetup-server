package com.meetup.server.event.application;

import com.meetup.server.event.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteCacheService {

    private final CacheManager cacheManager;

    @Value("${spring.redis.cache-name}")
    private String cacheName;

    public <T> T getCacheData(String cacheKey, Class<T> type) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            return cache.get(cacheKey, type);
        }
        return null;
    }

    public <T> void putCacheData(String cacheKey, T data) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(cacheKey, data);
        }
    }

    public RouteResponse updateCacheOfIsTransit(String cacheKey, boolean isTransit) {
        RouteResponse cached = getCacheData(cacheKey, RouteResponse.class);

        RouteResponse response = cached.updateIsTransit(isTransit);
        putCacheData(cacheKey, response);
        return response;
    }
}
