#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.tamirou.springboot.jwt.JWTSpringSecurity.JwtSpringSecurityApplication"]

#add the network
#docker network create my-network

#Mysql DB
#docker container run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=stage -d -p 3306:3306 --network my-network --name database mysql:5

FROM openjdk:8u232-jdk-slim-buster
#FROM tomcat:8.5.49-jdk8-openjdk
MAINTAINER Thierno Amirou DIALLO thiernoamiroud@gmail.com
#RUN rm -rf /usr/local/tomcat/webapps/*
RUN apt-get update && apt-get install -y iputils-ping
WORKDIR /usr/local/bin
COPY target/jwtspringsecurity-0.0.1-SNAPSHOT.jar /usr/local/bin/webapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-Dserver.port=8080", "-jar", "webapp.jar" ]
