package com.meetup.server.subway.persistence;

import com.meetup.server.subway.domain.Subway;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubwayRepository extends JpaRepository<Subway, Integer> {

    Optional<Subway> findByNameAndLine(String name, Integer line);

    Optional<Subway> findByCode(Integer code);

    @Query("""
                SELECT s
                FROM Subway s
                WHERE st_distance(s.point, :startPoint) = (
                    SELECT MIN(st_distance(s2.point, :startPoint))
                    FROM Subway s2)
            """)
    Subway findClosestSubway(@Param("startPoint") Point startPoint);

    @Query("""
                SELECT s
                FROM Subway s
                WHERE CAST(st_dwithin(s.point, :centerPoint, :radius, true) AS boolean) = true
            """)
    List<Subway> findAllWithinRadius(@Param("centerPoint") Point centerPoint, @Param("radius") double radius);
}
