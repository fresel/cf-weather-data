# Dockerfile for CF Weather Data API
# Multi-stage build to reduce final image size
# Uses the Temurin OpenJDK 21 base image

FROM maven:3.9.6-eclipse-temurin-21 as build
WORKDIR /app
COPY backend/cf-weather-api .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/cf-weather-api-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "cf-weather-api-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080