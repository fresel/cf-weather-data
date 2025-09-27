# Dockerfile for CF Weather Data API
# Multi-stage build to reduce final image size
# Uses the Temurin OpenJDK 21 base image

FROM maven:3.9.6-eclipse-temurin-21 as build
WORKDIR /app
COPY backend/cf-weather-api .
RUN mvn clean package -DskipTests
RUN cp target/cf-weather-api-*.jar cf-weather-api.jar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/cf-weather-api.jar .
ENTRYPOINT ["java", "-jar", "cf-weather-api.jar"]
EXPOSE 8080