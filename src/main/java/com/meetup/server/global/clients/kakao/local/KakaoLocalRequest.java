package com.meetup.server.global.clients.kakao.local;

import lombok.Builder;

@Builder
public record KakaoLocalRequest(
        String query,
        CategoryGroupCode categoryGroupCode,
        String x,
        String y,
        Integer radius,
        String rect,
        Integer page,
        Integer size,
        String sort
) {
}
