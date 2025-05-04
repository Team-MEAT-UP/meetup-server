package com.meetup.server.subway.persistence;

import com.meetup.server.subway.domain.SubwayConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubwayConnectionRepository extends JpaRepository<SubwayConnection, Long> {
}
