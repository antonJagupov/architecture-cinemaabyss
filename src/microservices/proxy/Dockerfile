FROM eclipse-temurin:17-alpine AS builder

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
WORKDIR /app
# Копируем JAR из стадии builder
COPY --from=builder /app/target/proxy-service-1.0.0.jar app.jar

# Устанавливаем здоровье приложения
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8000/health || exit 1

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]

