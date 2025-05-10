package com.meetup.server.user.dto.request;

public record UserAgreementRequest(
        boolean isPersonalInfoAgreement ,
        boolean isMarketingAgreement
) {
}
