FROM amazoncorretto:21

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 3500

ENTRYPOINT ["java", "-jar", "/app/app.jar"]