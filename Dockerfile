FROM maven:3.8-jdk-11-slim as build

WORKDIR /usr/local/app
COPY pom.xml ./
RUN mvn verify clean --fail-never

COPY src ./src
RUN mvn package

FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
WORKDIR /usr/local/app

COPY --from=build /usr/local/app/target/*.jar ./

ENV SERVER_PORT=8080

#run the app
CMD java -jar ./my-website-spring-0.0.1-SNAPSHOT.jar --server.port=${SERVER_PORT}