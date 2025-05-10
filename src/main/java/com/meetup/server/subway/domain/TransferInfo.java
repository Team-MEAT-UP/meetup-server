package com.meetup.server.subway.domain;

import com.meetup.server.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transfer_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"from_subway_id", "to_subway_id"})})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class TransferInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_info_id")
    private Integer transferInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_subway_id", nullable = false)
    private Subway fromSubway;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_subway_id", nullable = false)
    private Subway toSubway;

    @Builder
    public TransferInfo(Subway fromSubway, Subway toSubway) {
        this.fromSubway = fromSubway;
        this.toSubway = toSubway;
    }
}
