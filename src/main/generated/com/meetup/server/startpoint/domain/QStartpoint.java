package com.meetup.server.startpoint.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStartpoint is a Querydsl query type for Startpoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStartpoint extends EntityPathBase<Startpoint> {

    private static final long serialVersionUID = 679563524L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStartpoint startpoint = new QStartpoint("startpoint");

    public final com.meetup.server.global.domain.QBaseEntity _super = new com.meetup.server.global.domain.QBaseEntity(this);

    public final com.meetup.server.startpoint.domain.type.QAddress address;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.meetup.server.event.domain.QEvent eventId;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final com.meetup.server.startpoint.domain.type.QLocation location;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> startPointId = createNumber("startPointId", Long.class);

    public QStartpoint(String variable) {
        this(Startpoint.class, forVariable(variable), INITS);
    }

    public QStartpoint(Path<? extends Startpoint> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStartpoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStartpoint(PathMetadata metadata, PathInits inits) {
        this(Startpoint.class, metadata, inits);
    }

    public QStartpoint(Class<? extends Startpoint> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.meetup.server.startpoint.domain.type.QAddress(forProperty("address")) : null;
        this.eventId = inits.isInitialized("eventId") ? new com.meetup.server.event.domain.QEvent(forProperty("eventId"), inits.get("eventId")) : null;
        this.location = inits.isInitialized("location") ? new com.meetup.server.startpoint.domain.type.QLocation(forProperty("location")) : null;
    }

}

