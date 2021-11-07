FROM openjdk:11
VOLUME /tmp
RUN mkdir -p /config
ADD ./build/libs/io-moto-challange-1.0-SNAPSHOT.jar app.jar
ADD ./src/main/resources/application.properties /config/
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]