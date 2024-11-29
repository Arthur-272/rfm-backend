# Use the official OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Spring Boot JAR into the container
ARG JAR_FILE=target/RFM-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
