# ✅ Use a stable, widely available OpenJDK base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
