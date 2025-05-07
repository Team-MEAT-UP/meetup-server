package com.meetup.server.event.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meetup.server.global.clients.odsay.OdsayTransitRouteSearchResponse;
import com.meetup.server.subway.dto.response.PassStopList;
import com.meetup.server.subway.dto.response.Stations;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record TransitRouteResponse(
        int trafficType,    //1: 지하철, 2: 버스, 3: 도보
        String startExitNo,
        String endExitNo,
        double distance,
        String laneName,  //지하철 노선명
        String startBoardName,
        String endBoardName,
        int stationCount,
        PassStopList passStopList,
        int sectionTime //이동 소요 시간
) {
    public static List<TransitRouteResponse> from(OdsayTransitRouteSearchResponse response) {

        return response.data().path().getFirst().subPath().stream()
                .map(subPath -> {
                    String laneName = Optional.ofNullable(subPath.lane())
                            .flatMap(lanes -> lanes.stream().findFirst())
                            .map(lane -> switch (subPath.trafficType()) {
                                case 1 -> lane.name();   // 지하철
                                case 2 -> lane.busNo();  // 버스
                                case 3 -> lane.name();   // 도보
                                default -> null;
                            })
                            .orElse(null);

                    List<Stations> passStopList = convertToDomainStations(
                            Optional.ofNullable(subPath.passStopList())
                                    .map(OdsayTransitRouteSearchResponse.TransitData.PassStopList::stations)
                                    .orElse(List.of()));

                    return TransitRouteResponse.builder()
                            .trafficType(subPath.trafficType())
                            .distance(subPath.distance())
                            .laneName(laneName)
                            .startBoardName(subPath.startName())
                            .endBoardName(subPath.endName())
                            .stationCount(subPath.stationCount() != null ? subPath.stationCount() : 0)
                            .passStopList(subPath.trafficType() == 3 ? null : new PassStopList(passStopList))
                            .startExitNo(subPath.startExitNo())
                            .endExitNo(subPath.endExitNo())
                            .sectionTime(subPath.sectionTime())
                            .build();
                })
                .toList();

    }

    private static List<Stations> convertToDomainStations(List<OdsayTransitRouteSearchResponse.TransitData.Station> odsayStations) {
        if (odsayStations == null || odsayStations.isEmpty()) {
            return List.of();
        }
        return odsayStations.stream()
                .map(station -> new Stations(
                        station.index(),
                        station.stationName(),
                        station.x(),
                        station.y()
                ))
                .toList();
    }
}
