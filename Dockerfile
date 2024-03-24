
# Build stage
FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:go-offline
COPY src src
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package

# Final stage
FROM openjdk:17
WORKDIR /app
COPY --from=build /build/target/evento-0.0.1-SNAPSHOT.jar.jar /app/Evento.jar
EXPOSE 8080
CMD ["java", "-jar", "evento.jar"]