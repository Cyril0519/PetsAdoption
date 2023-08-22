FROM openjdk:8u201-jre-alpine

MAINTAINER wxy

RUN mkdir -p /pet/webgateway

WORKDIR /pet/webgateway

EXPOSE 8001

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx512m"

ADD ./target/petsAdoption_gateway_web-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar", "${JAVA_OPTS}"]

