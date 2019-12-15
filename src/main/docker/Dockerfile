#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.tamirou.springboot.jwt.JWTSpringSecurity.JwtSpringSecurityApplication"]

#add the network
#docker network create my-network

#Mysql DB & the service
#docker container run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=stage -d -p 3306:3306 --network my-network --name database mysql:5
#docker container run -d --network my-network --name spring-boot-jwt -p 80:8080 thiernoamiroud/spring-boot-jwt:mysqldb-docker-maven-plugin
#Swarm
#docker network create --driver overlay my-network
#docker service create -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=stage -d -p 3306:3306 --network my-network --name database mysql:5
#docker service create -d --network my-network --name spring-boot-jwt -p 80:8080 thiernoamiroud/spring-boot-jwt:mysqldb-docker-maven-plugin
#docker run -it -d -p 5000:8080 -v /var/run/docker.sock:/var/run/docker.sock dockersamples/visualizer

FROM openjdk:8u232-jdk-slim-buster
#FROM tomcat:8.5.49-jdk8-openjdk
MAINTAINER Thierno Amirou DIALLO thiernoamiroud@gmail.com
#RUN rm -rf /usr/local/tomcat/webapps/*
RUN apt-get update && apt-get install -y iputils-ping
WORKDIR /usr/local/bin
#maven dir is relative to the MDP ==> target/docker/thiernoamiroud/spring-boot-jwt/build/maven
COPY maven/jwtspringsecurity-0.0.1-SNAPSHOT.jar /usr/local/bin/webapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-Dserver.port=8080", "-jar", "webapp.jar" ]
