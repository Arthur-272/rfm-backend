# Stage 1: Build Stage
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the project using Maven
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the Spring Boot JAR from the build stage into the runtime image
COPY --from=build /app/target/RFM-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
