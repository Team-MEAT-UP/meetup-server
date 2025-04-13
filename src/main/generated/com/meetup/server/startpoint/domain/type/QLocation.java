package com.meetup.server.startpoint.domain.type;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocation is a Querydsl query type for Location
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLocation extends BeanPath<Location> {

    private static final long serialVersionUID = -1503177261L;

    public static final QLocation location = new QLocation("location");

    public final StringPath roadLatitude = createString("roadLatitude");

    public final StringPath roadLongitude = createString("roadLongitude");

    public QLocation(String variable) {
        super(Location.class, forVariable(variable));
    }

    public QLocation(Path<? extends Location> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocation(PathMetadata metadata) {
        super(Location.class, metadata);
    }

}

