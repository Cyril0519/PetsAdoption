FROM openjdk:8u201-jre-alpine

MAINTAINER wxy

RUN mkdir -p /pet/user

WORKDIR /pet/user

EXPOSE 8083

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx512m"

ADD ./target/petsAdoption_wishList-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar", "${JAVA_OPTS}"]

