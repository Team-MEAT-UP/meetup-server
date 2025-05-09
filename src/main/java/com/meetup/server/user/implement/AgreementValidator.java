package com.meetup.server.user.implement;

import com.meetup.server.user.exception.UserErrorType;
import com.meetup.server.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgreementValidator {

    public void validateAgreements(boolean personalInfoAgreement) {
        if (!personalInfoAgreement) {
            throw new UserException(UserErrorType.AGREEMENT_REQUIRED);
        }
    }
}
