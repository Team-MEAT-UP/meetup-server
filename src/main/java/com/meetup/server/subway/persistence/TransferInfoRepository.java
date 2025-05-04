package com.meetup.server.subway.persistence;

import com.meetup.server.subway.domain.TransferInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferInfoRepository extends JpaRepository<TransferInfo, Long> {
}
