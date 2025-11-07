# === 1. Билд-этап: собираем JAR с помощью Maven ===
FROM eclipse-temurin:17-alpine AS builder

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем pom.xml и исходники
COPY pom.xml .
COPY src ./src

# Устанавливаем Maven и собираем приложение
RUN apt-get update && apt-get install -y maven && \
    mvn -B -q clean package -DskipTests

FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8082


ENTRYPOINT ["java", "-jar", "app.jar"]
