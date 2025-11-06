# === 1. Билд-этап: собираем JAR с помощью Maven ===
FROM eclipse-temurin:17-alpine AS builder

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем pom.xml и исходники
COPY pom.xml .
COPY src ./src

# Устанавливаем Maven и собираем приложение
RUN apk update && apk add --no-cache maven && \
    mvn clean package -DskipTests


FROM eclipse-temurin:17-alpine

WORKDIR /app

# Копируем JAR файл
COPY --from=builder /app/target/*.jar app.jar

# Устанавливаем здоровье приложения
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8000/health || exit 1

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]



