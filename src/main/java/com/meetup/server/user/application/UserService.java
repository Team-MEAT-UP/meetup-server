package com.meetup.server.user.application;

import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.implement.StartPointReader;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.dto.response.UserEventHistoryResponse;
import com.meetup.server.user.dto.response.UserProfileInfoResponse;
import com.meetup.server.user.implement.AgreementValidator;
import com.meetup.server.user.implement.UserEventHistoryAssembler;
import com.meetup.server.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final StartPointReader startPointReader;
    private final AgreementValidator agreementValidator;
    private final UserReader userReader;
    private final UserEventHistoryAssembler userEventHistoryAssembler;

    public UserProfileInfoResponse getUserProfileInfo(Long userId) {
        return UserProfileInfoResponse.from( userReader.read(userId));
    }

    @Transactional
    public void saveUserAgreement(Long userId, boolean personalInfoAgreement, boolean marketingAgreement) {
        agreementValidator.validateAgreements(personalInfoAgreement);

        User user = userReader.read(userId);
        user.updateAgreement(personalInfoAgreement, marketingAgreement);
    }

    public List<UserEventHistoryResponse> getUserEventHistory(Long userId) {
        List<StartPoint> userStartPoints = startPointReader.readAll(userId);
        List<StartPoint> allEventStartPoints = startPointReader.readAll(userStartPoints);

        return userEventHistoryAssembler.assemble(userStartPoints, allEventStartPoints, userId);
    }
}
