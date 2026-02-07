# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom first for better caching
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

# Eureka default port (change if you use different)
EXPOSE 8761

# Run Eureka Server
ENTRYPOINT ["java","-jar","/app/app.jar"]
