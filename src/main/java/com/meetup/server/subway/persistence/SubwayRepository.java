package com.meetup.server.subway.persistence;

import com.meetup.server.subway.domain.Subway;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubwayRepository extends JpaRepository<Subway, Long> {

    Optional<Subway> findByNameAndLine(String name, Integer line);

    Optional<Subway> findByCode(Integer code);
}
