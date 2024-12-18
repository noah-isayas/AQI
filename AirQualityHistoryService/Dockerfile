FROM openjdk:21
COPY ./target/AirQualityHistoryService-0.0.1-SNAPSHOT.jar /usr/src/AirQualityHistoryService/
WORKDIR /usr/src/AirQualityHistoryService/
EXPOSE 8081
CMD ["java", "-jar", "AirQualityHistoryService-0.0.1-SNAPSHOT.jar"]