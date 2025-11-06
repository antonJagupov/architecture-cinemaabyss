# === 1. Билд-этап: собираем JAR с помощью Maven ===
FROM eclipse-temurin:11-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# Стадия запуска
FROM eclipse-temurin:11-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]
