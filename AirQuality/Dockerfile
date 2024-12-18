FROM openjdk:21
COPY ./target/AirQuality-0.0.1-SNAPSHOT.jar /usr/src/AirQuality/
WORKDIR /usr/src/AirQuality/
EXPOSE 8081
CMD ["java", "-jar", "AirQuality-0.0.1-SNAPSHOT.jar"]