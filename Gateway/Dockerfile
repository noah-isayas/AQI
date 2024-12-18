FROM openjdk:21
COPY ./target/Gateway-0.0.1-SNAPSHOT.jar /usr/src/Gateway/
WORKDIR /usr/src/Gateway/
EXPOSE 8000
CMD ["java", "-jar", "Gateway-0.0.1-SNAPSHOT.jar"]