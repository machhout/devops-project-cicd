# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Expose the port your application runs on
EXPOSE 8087

# Set the working directory
WORKDIR /app

# Install wget to download the JAR file
RUN apk add --no-cache wget

# Download the JAR from Nexus (ensure this URL directly points to the JAR file)
RUN wget -O /app/tpfoyer.jar "http://192.168.35.4:8081/repository/maven-releases/tn/esprit/tp-foyer/5.0.0/tp-foyer-5.0.0.jar"

# Entry point to run the application
ENTRYPOINT ["java", "-jar", "tpfoyer.jar"]
