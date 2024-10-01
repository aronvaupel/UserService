
FROM gradle:8.10.2-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM alpine/java:21-jdk
WORKDIR /app
RUN apk update && \
    apk add --no-cache docker
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
COPY docker-compose.yml /app/docker-compose.yml
ENTRYPOINT ["java", "-jar", "/app/app.jar"]