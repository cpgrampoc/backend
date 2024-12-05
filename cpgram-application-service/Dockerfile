# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the .jar file from the host machine to the /app directory in the container
COPY target/cpgram-application-service-0.0.1-SNAPSHOT.jar /app/cpgram-application-service.jar

# Expose port 8080 (default port for Spring Boot)
EXPOSE 8087

# Run the .jar file using the `java` command
CMD ["java", "-jar", "/app/cpgram-application-service.jar"]
