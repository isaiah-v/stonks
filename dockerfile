FROM adoptopenjdk:11-jre-hotspot

RUN mkdir /opt/app
COPY build/libs/stonks.jar /opt/app

CMD ["java", "-jar", "/opt/app/stonks.jar"]