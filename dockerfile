FROM adoptopenjdk:11-jre-hotspot

RUN mkdir /app/workspace
COPY build/libs/stonks.jar /app

WORKDIR /app/workspace

CMD ["java", "-jar", "/app/stonks.jar"]
