FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /notification

COPY ../util/pom.xml /util/pom.xml
WORKDIR /util
RUN mvn dependency:go-offline -B

COPY ../util .
RUN mvn -f /util/pom.xml clean install -DskipTests

WORKDIR /notification

COPY ./notification/pom.xml .
COPY ./notification/notification-model/pom.xml ./notification-model/pom.xml
COPY ./notification/notification-repository/pom.xml ./notification-repository/pom.xml
COPY ./notification/notification-service/pom.xml ./notification-service/pom.xml
COPY ./notification/notification-web/pom.xml ./notification-web/pom.xml
COPY ./notification/notification-app/pom.xml ./notification-app/pom.xml

RUN mvn dependency:go-offline -B

COPY ./notification .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /notification
COPY --from=build /notification/notification-app/target/*.jar notification.jar
EXPOSE 3004
ENTRYPOINT ["java", "-jar", "notification.jar"]
