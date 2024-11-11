FROM openjdk
COPY gregorian-api.jar /gregorian/gregorian-api.jar
WORKDIR /gregorian
EXPOSE 8080
CMD [ "java", "-jar", "gregorian-api.jar" ]