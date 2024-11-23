FROM eclipse-temurin:17.0.8.1_1-jre-jammy
WORKDIR /app
COPY /target/swe-task-service.jar swe-task-service.jar
ENTRYPOINT ["java", "-jar", "swe-task-service.jar"]
EXPOSE 7001 7010