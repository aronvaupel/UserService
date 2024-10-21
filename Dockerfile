# Stage 1: Build the application
FROM gradle:8.10.2-jdk21 AS build

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

# Clone the repository from GitHub
RUN git clone https://${GITHUB_USERNAME}:${GITHUB_TOKEN}@github.com/aronvaupel/UserService.git /home/gradle/src

WORKDIR /home/gradle/src

# Build the project
RUN gradle build --no-daemon

# Stage 2: Create a smaller image for running the application
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

RUN apk update && \
    apk add --no-cache redis

# Copy the built jar from the previous stage
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
