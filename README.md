## Spring Secutiry with JWT and docker containerization
This project is a demo of spring security with jwt and docker containerization with docker

It's based on 
- https://grokonez.com/spring-framework/spring-boot/spring-security-jwt-authentication-postgresql-restapis-springboot-spring-mvc-spring-jpa and
- https://medium.com/@shrikarvk/creating-a-docker-container-for-spring-boot-app-d5ff1050c14f


 to build : mvnw install dockerfile:build
 to run : docker run --add-host localhost:192.168.2.14 -p 8081:8080 -d -t mydocker/jwtspringsecurity : d
 to connect to the shell : sudo docker exec -ti jwtspringsecurity sh