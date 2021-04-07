FROM adoptopenjdk:11-jre-hotspot

RUN mkdir -p /app/workspace
COPY build/libs/stonks.jar /app

WORKDIR /app/workspace

CMD ["java", "-Djava.security.egd=file:/dev/urandom", "-jar", "/app/stonks.jar"]
