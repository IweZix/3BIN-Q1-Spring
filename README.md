# 3BIN-Q1-Spring

## What is Spring ? 

Spring is a framework used to develop Java web applications. 
In this course, we use Spring to create a microservice architecture.

## What is a microservice architecture ?

A microservice architecture is a way to develop applications as a suite 
of small services, each running in its own process but all 
application communicate with each other using request/response mechanism.

## Dependencies

In this course, we use the following dependencies:

- [Spring Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- [Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
- [Spring Data JPA](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
- [H2 Database](https://mvnrepository.com/artifact/com.h2database/h2)
- [Eureka Discovery Client](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client)
- [Eureka Server](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-server)
- [Feign](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign)
- [JWT]()
- [jbcrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)

## How to run the project ?

Open a project (./Course`[number]`/`[AmazingProductService or Amazing]`). In each project, you find some microservices.

In each microservice, you have to run the main class `[microservice name]Application.java`.