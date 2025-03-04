FROM maven AS build

COPY src /app/src

COPY pom.xml /app

WORKDIR /app

RUN mvn clean install -DskipTests=true

FROM openjdk:23-ea-17-jdk-slim

COPY --from=build /app/target/*.jar /app/gregorian-api.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java","-jar", "gregorian-api.jar"]