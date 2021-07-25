FROM adoptopenjdk/openjdk16:ubi
COPY target/*.jar notification.jar
ENTRYPOINT ["java", "-jar", "notification.jar"]