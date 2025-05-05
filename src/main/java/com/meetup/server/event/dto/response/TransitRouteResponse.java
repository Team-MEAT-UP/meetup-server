package com.meetup.server.event.dto.response;

import com.meetup.server.global.clients.odsay.OdsayTransitRouteSearchResponse;
import com.meetup.server.subway.dto.repsonse.PassStopList;
import com.meetup.server.subway.dto.repsonse.Stations;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public record TransitRouteResponse(
        int trafficType,    //1: 지하철, 2: 버스, 3: 도보
        String laneName,  //지하철 노선명
        String startBoardName,
        String endBoardName,
        int stationCount,
        PassStopList passStopList,
        int sectionTime //이동 소요 시간
) {
    public static List<TransitRouteResponse> of(OdsayTransitRouteSearchResponse response) {
        return response.data().path().stream()
                .flatMap(path -> path.subPath().stream())
                .filter(subPath -> subPath.trafficType() != 3)
                .map(subPath -> {
                    String laneName = Optional.ofNullable(subPath.lane())
                            .flatMap(lanes -> lanes.stream().findFirst())
                            .map(lane -> switch (subPath.trafficType()) {
                                case 1 -> lane.name();   // 지하철
                                case 2 -> lane.busNo();  // 버스
                                default -> null;
                            })
                            .orElse(null);

                    List<Stations> passStopList = convertToDomainStations(subPath.passStopList().stations());

                    return TransitRouteResponse.builder()
                            .trafficType(subPath.trafficType())
                            .laneName(laneName)
                            .startBoardName(subPath.startName())
                            .endBoardName(subPath.endName())
                            .stationCount(subPath.stationCount() != null ? subPath.stationCount() : 0)
                            .passStopList(new PassStopList(passStopList))
                            .sectionTime(subPath.sectionTime())
                            .build();
                })
                .toList();

    }

    private static List<Stations> convertToDomainStations(List<OdsayTransitRouteSearchResponse.TransitData.Station> odsayStations) {
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
