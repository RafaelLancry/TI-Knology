FROM eclipse-temurin:21-jdk-alpine

VOLUME /tmp

COPY target/*.jar app.jar

COPY start.sh /start.sh

RUN chmod +x /start.sh

EXPOSE 8080

ENTRYPOINT ["/start.sh"]