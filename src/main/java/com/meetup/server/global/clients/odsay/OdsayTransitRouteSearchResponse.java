package com.meetup.server.global.clients.odsay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OdsayTransitRouteSearchResponse(
        @JsonProperty("result") TransitData data
) {
    public record TransitData(
            int searchType,
            int outTrafficCheck,
            int busCount,
            int subwayCount,
            int subwayBusCount,
            double pointDistance,
            int startRadius,
            int endRadius,
            List<Path> path
    ) {
        public record Path(
                int pathType,
                Info info,
                List<SubPath> subPath
        ) {
        }

        public record Info(
                double trafficDistance,
                int totalWalk,
                int totalTime,
                int payment,
                int busTransitCount,
                int subwayTransitCount,
                String mapObj,
                String firstStartStation,
                String firstStartStationKor,
                String firstStartStationJpnKata,
                String lastEndStation,
                String lastEndStationKor,
                String lastEndStationJpnKata,
                int totalStationCount,
                int busStationCount,
                int subwayStationCount,
                double totalDistance,
                int checkIntervalTime,
                String checkIntervalTimeOverYn,
                int totalIntervalTime
        ) {
        }

        public record SubPath(
                int trafficType,
                double distance,
                int sectionTime,
                Integer stationCount,
                List<Lane> lane,
                int intervalTime,
                String startName,
                String startNameKor,
                String startNameJpnKata,
                double startX,
                double startY,
                String endName,
                String endNameKor,
                String endNameJpnKata,
                double endX,
                double endY,
                String way,
                Integer wayCode,
                String door,
                int startID,
                Integer startStationCityCode,
                Integer startStationProviderCode,
                String startLocalStationID,
                String startArsID,
                int endID,
                Integer endStationCityCode,
                Integer endStationProviderCode,
                String endLocalStationID,
                String endArsID,
                String startExitNo,
                Double startExitX,
                Double startExitY,
                String endExitNo,
                Double endExitX,
                Double endExitY,
                PassStopList passStopList
        ) {
        }

        public record Lane(
                String name,
                String nameKor,
                String nameJpnKata,
                String busNo,
                String busNoKor,
                String busNoJpnKata,
                Integer type,
                Integer busID,
                String busLocalBlID,
                Integer busCityCode,
                Integer busProviderCode,
                Integer subwayCode,
                Integer subwayCityCode
        ) {
        }

        public record PassStopList(
                List<Station> stations
        ) {
        }

        public record Station(
                int index,
                int stationID,
                String stationName,
                String stationNameKor,
                String stationNameJpnKata,
                Integer stationCityCode,
                Integer stationProviderCode,
                String localStationID,
                String arsID,
                String x,
                String y,
                String isNonStop
        ) {
        }
    }
}
