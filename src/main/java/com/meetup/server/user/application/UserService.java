package com.meetup.server.user.application;

import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.implement.StartPointReader;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.dto.response.UserEventHistoryResponse;
import com.meetup.server.user.dto.response.UserProfileInfoResponse;
import com.meetup.server.user.exception.UserErrorType;
import com.meetup.server.user.exception.UserException;
import com.meetup.server.user.implement.AgreementValidator;
import com.meetup.server.user.implement.UserEventHistoryAssembler;
import com.meetup.server.user.persistence.UserRepository;
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

    private final UserRepository userRepository;
    private final AgreementValidator agreementValidator;
    private final StartPointReader startPointReader;
    private final UserEventHistoryAssembler userEventHistoryAssembler;

    public UserProfileInfoResponse getUserProfileInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorType.USER_NOT_FOUND));

        return UserProfileInfoResponse.from(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorType.USER_NOT_FOUND));
    }

    @Transactional
    public void saveUserAgreement(Long userId, boolean personalInfoAgreement, boolean marketingAgreement) {
        agreementValidator.validateAgreements(personalInfoAgreement);

        User user = getUserById(userId);
        user.updateAgreement(personalInfoAgreement, marketingAgreement);
    }

    public List<UserEventHistoryResponse> getUserEventHistory(Long userId) {
        List<StartPoint> userStartPoints = startPointReader.readAll(userId);
        List<StartPoint> allEventStartPoints = startPointReader.readAll(userStartPoints);

        return userEventHistoryAssembler.assemble(userStartPoints, allEventStartPoints);
    }
}
