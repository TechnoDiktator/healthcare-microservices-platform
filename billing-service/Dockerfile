FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk AS runner


RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*


WORKDIR /app

COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar ./app.jar

#for the springboot tomcat server
EXPOSE 4001

#for the grpc server
EXPOSE 9001

ENTRYPOINT ["java" , "-jar" , "app.jar"]
#
#                  Billing Service
#        +--------------------------------+
#        |                                |
#        |  Spring Boot Application        |
#        |                                |
#        |   +------------------------+   |
#HTTP <---> | Tomcat Server           |   |
#4001       | (REST APIs)             |   |
#        |   +------------------------+   |
#        |                                |
#gRPC <---> | gRPC Netty Server        |   |
#9001       | (gRPC Services)          |   |
#        |   +------------------------+   |
#        +--------------------------------+


#CHAT GPT notes
#
#
#Why Tomcat uses port 4001
#
#If you have:
#
#server.port=4001
#
#then Tomcat listens on:
#
#http://localhost:4001
#
#This is for REST endpoints like:
#
#GET /patients
#POST /billing
#Why gRPC uses port 9001
#
#If you have:
#
#grpc.server.port=9001
#
#then the gRPC server listens on:
#
#localhost:9001
#
#This is not HTTP REST.
#
#It accepts protobuf messages like:
#
#CreateBillingAccount(
#    patientId,
#    name,
#    email
#)
#
#using HTTP/2.