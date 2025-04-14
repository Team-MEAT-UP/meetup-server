FROM eclipse-temurin:21-alpine

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

ARG JAR_FILE=./*.jar
COPY ${JAR_FILE} spot.jar

ENV DB_URL=${DB_URL} \
    DB_USERNAME=${DB_USERNAME} \
    DB_PASSWORD=${DB_PASSWORD}

ENTRYPOINT ["java", "-jar", "spot.jar", "--spring.profiles.active=dev"]
