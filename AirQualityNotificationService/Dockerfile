FROM openjdk:21
COPY ./target/AirQualityNotificationService-0.0.1-SNAPSHOT.jar /usr/src/AirQualityNotificationService/
WORKDIR /usr/src/AirQualityNotificationService/
EXPOSE 8081
CMD ["java", "-jar", "AirQualityNotificationService-0.0.1-SNAPSHOT.jar"]