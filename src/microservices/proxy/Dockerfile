# === 1. Билд-этап: собираем JAR с помощью Maven ===
FROM openjdk:17-jdk-slim AS builder

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем pom.xml и исходники
COPY pom.xml .
COPY src ./src

# Устанавливаем Maven и собираем приложение
RUN apt-get update && apt-get install -y maven && \
    mvn -B -q clean package -DskipTests


FROM openjdk:17-jdk-slim

WORKDIR /app

# Копируем JAR файл
COPY target/proxy-service-1.0.0.jar app.jar

# Устанавливаем здоровье приложения
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8000/health || exit 1

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]