# 🧱 Stage 1: Build the application JAR
FROM maven:3.9.6-eclipse-temurin-21 AS builder


WORKDIR /build
COPY . .

# Skip tests to speed up container build
RUN mvn clean package -DskipTests

# 🚀 Stage 2: Run the JAR in a lightweight image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the jar from the build stage
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
