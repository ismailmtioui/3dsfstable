# Use an official Maven image to build the project
FROM maven:3.8.1-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml ./

# Download all dependencies, including the ones for test scope
RUN mvn dependency:go-offline

# Copy the entire project to the container
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a base image for the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged application from the build stage
COPY --from=build /app/target/je-0.0.1-SNAPSHOT.jar /app/je-0.0.1-SNAPSHOT.jar

# Expose the port on which the application will run (8089 for the spring-boot-app service)
EXPOSE 8080

# Set default environment variables for the application
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "/app/je-0.0.1-SNAPSHOT.jar"]
