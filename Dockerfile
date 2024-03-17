FROM openjdk:17-slim

WORKDIR /app

COPY pom.xml ./

RUN mvn clean install -DskipTests

EXPOSE 8111


COPY target/evento-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]