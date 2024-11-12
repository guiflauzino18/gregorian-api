FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /gregorian

COPY ../pom.xml .
COPY ../src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /gregorian
COPY --from=build /gregorian/target/*.jar gregorian-api.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "gregorian-api.jar" ]