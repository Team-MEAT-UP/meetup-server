package com.meetup.server.global.clients.odsay;

import lombok.Builder;

@Builder
public record OdsayTransitRouteSearchRequest(
        String sx,  //출발지 경도
        String sy,  //출발지 위도
        String ex,  //도착지 경도
        String ey,  //도착지 위도
        Integer opt,    // 0: 추천경로 1: 타입별 정렬
        Integer searchType, // 도시간 이동 or 도시내 이동(:0) 검색
        Integer searchPathType //도시내 경로 이동수단 (0: ALL, 1: 지하철, 2: 버스)
) {
}
