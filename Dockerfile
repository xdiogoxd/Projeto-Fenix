FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/Fenix-0.0.1-SNAPSHOT.jar  /app/projeto-fenix.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/projeto-fenix.jar" ]