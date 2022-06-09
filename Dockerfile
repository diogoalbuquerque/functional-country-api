FROM openjdk:17.0.2-jdk

ADD . /functional-country-api
WORKDIR /functional-country-api

RUN ./gradlew clean build

ENTRYPOINT ["java", "-jar","./application/build/libs/application-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
