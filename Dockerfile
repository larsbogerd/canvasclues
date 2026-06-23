# syntax=docker/dockerfile:1

# --- Build stage: compile the Spring Boot fat JAR ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cache dependencies first: only pom.xml changes invalidate this layer
COPY pom.xml .
COPY .mvn/ .mvn/
RUN mvn -B dependency:go-offline

# Now copy sources and build. Skip tests/checkstyle for a fast, deterministic deploy build.
COPY checkstyle.xml .
COPY src ./src
RUN mvn -B clean package -Dmaven.test.skip=true

# --- Runtime stage: slim JRE with just the JAR ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
