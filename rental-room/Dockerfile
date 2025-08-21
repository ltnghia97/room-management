FROM openjdk:17-jdk-slim
COPY build/libs/rental-room-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 9998
ENTRYPOINT ["java", "-jar", "rental-room-0.0.1-SNAPSHOT.jar"]

