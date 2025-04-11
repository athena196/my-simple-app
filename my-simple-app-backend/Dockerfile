# Java Spring Boot Dockerfile
FROM amazoncorretto:21

COPY target/my-simple-app-0.0.1-SNAPSHOT.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.war"]