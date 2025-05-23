# ------------------------------
# Stage 1: Build
# ------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the entire service source code
COPY . .

# Package the application (this creates the target/*.jar)
RUN mvn clean package -DskipTests

# ------------------------------
# Stage 2: Run
# ------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=build /app/target/*.jar app.jar

# Expose port (make sure it matches server.port in application.yml)
EXPOSE 8085

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
