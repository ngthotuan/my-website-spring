FROM openjdk:8-jdk-alpine as base

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

#FROM base as test
#CMD ["./mvnw", "test"]
#
#FROM base as development
#CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:8-jre-alpine as production
WORKDIR /app

ENV DB_CONNECTION_STRING=jdbc:mysql://localhost:3306/web_db
ENV DB_USER=root
ENV DB_PASSWORD=root
ENV APP_HOST_NAME=localhost:8080
ENV APP_UPLOAD_LOCATION=user-upload

EXPOSE 8080
COPY --from=build /app/target/*.jar ./
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/my-website-spring.jar"]
