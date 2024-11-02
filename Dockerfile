# Use an official JDK runtime as the base image
#FROM openjdk:17-jdk-slim

FROM openjdk:23-jdk-slim
# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/encryptor_decryptor-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
