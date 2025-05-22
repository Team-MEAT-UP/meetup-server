package com.meetup.server.user.application;

import com.meetup.server.global.util.TimeUtil;
import com.meetup.server.review.implement.ReviewChecker;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.implement.StartPointReader;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.dto.response.UserEventHistoryProjection;
import com.meetup.server.user.dto.response.UserEventHistoryResponse;
import com.meetup.server.user.dto.response.UserEventHistoryResponseList;
import com.meetup.server.user.dto.response.UserProfileInfoResponse;
import com.meetup.server.user.implement.AgreementValidator;
import com.meetup.server.user.implement.UserEventHistoryAssembler;
import com.meetup.server.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final StartPointReader startPointReader;
    private final AgreementValidator agreementValidator;
    private final UserReader userReader;
    private final UserEventHistoryAssembler userEventHistoryAssembler;
    private final ReviewChecker reviewChecker;

    public UserProfileInfoResponse getUserProfileInfo(Long userId) {
        return UserProfileInfoResponse.from(userReader.read(userId));
    }

    @Transactional
    public void saveUserAgreement(Long userId, boolean personalInfoAgreement, boolean marketingAgreement) {
        agreementValidator.validateAgreements(personalInfoAgreement);

        User user = userReader.read(userId);
        user.updateAgreement(personalInfoAgreement, marketingAgreement);
    }

    public List<UserEventHistoryResponse> getUserEventHistoryNoPaging(Long userId) {
        List<StartPoint> userStartPoints = startPointReader.readAll(userId);
        List<StartPoint> allEventStartPoints = startPointReader.readAll(userStartPoints);

        return userEventHistoryAssembler.assemble(userStartPoints, allEventStartPoints, userId);
    }

    public UserEventHistoryResponseList getUserEventHistory(Long userId, UUID lastViewedEventId, int size) {

        List<UserEventHistoryProjection> projections = startPointReader.findUserEventsWithPaging(userId, lastViewedEventId, size + 1);

        boolean hasNext = projections.size() > size;

        if (hasNext) {
            projections = projections.subList(0, size);
        }

        List<UUID> eventIds = projections.stream()
                .map(UserEventHistoryProjection::eventId)
                .toList();

        Map<UUID, List<User>> participantsMap = startPointReader.findParticipantsByEventIds(eventIds).stream()
                .collect(Collectors.groupingBy(sp -> sp.getEvent().getEventId(),
                        Collectors.mapping(StartPoint::getUser, Collectors.toList())));

        Map<UUID, Boolean> reviewMap = reviewChecker.isReviewed(projections, userId);

        List<UserEventHistoryResponse> responses = projections.stream()
                .map(p -> {
                    List<User> users = participantsMap.getOrDefault(p.eventId(), List.of());
                    return UserEventHistoryResponse.builder()
                            .eventId(p.eventId())
                            .middlePointName(p.subwayName())
                            .placeName(p.placeName())
                            .participatedPeopleCount(users.size())
                            .userProfileImageUrls(users.stream().map(User::getProfileImage).toList())
                            .eventMadeAgo(TimeUtil.calculateDaysAgo(p.createdAt()))
                            .eventTimeAgo(TimeUtil.calculateTimeAgo(p.createdAt()))
                            .isReviewed(reviewMap.getOrDefault(p.eventId(), false))
                            .build();
                }).toList();

        UUID lastId = responses.isEmpty() ? null : responses.get(responses.size() - 1).eventId();

        return UserEventHistoryResponseList.of(responses, hasNext, lastId);
    }
}
