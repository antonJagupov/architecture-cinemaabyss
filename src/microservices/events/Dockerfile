FROM openjdk:11-jre-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven && \
    mvn -B -q clean package -DskipTests

FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8082


ENTRYPOINT ["java", "-jar", "app.jar"]
