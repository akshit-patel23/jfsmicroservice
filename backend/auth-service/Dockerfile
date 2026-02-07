# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom first (dependency cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build jar
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Auth service port (confirm from application.yml)
EXPOSE 8084

# Run application
ENTRYPOINT ["java","-jar","/app/app.jar"]
