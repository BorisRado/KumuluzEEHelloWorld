FROM adoptopenjdk/openjdk14:alpine-slim

RUN mkdir /app
WORKDIR /app

COPY ./api/target/api-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]