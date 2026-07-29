FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:21-jdk AS runner

RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*



WORKDIR /app

COPY --from=builder ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4000

ENTRYPOINT ["java" , "-jar" , "app.jar"]


#
#Stage 1: Build the application
#FROM maven:3.9.9-eclipse-temurin-21 AS builder
#Uses an official Docker image that already contains:
#Maven 3.9.9
#Eclipse Temurin JDK 21
#AS builder gives this stage a name (builder) so it can be referenced later.
#
#Think of this as a temporary machine used only to compile your application.
#
#WORKDIR /app
#
#Creates (if necessary) and switches to the /app directory.
#
#Every subsequent command runs from here.
#
#Equivalent to:
#
#mkdir /app
#cd /app
#COPY pom.xml .
#
#Copies only the pom.xml file from your local machine into the container.
#
#Local
#------
#pom.xml
#
#↓
#
#Container
#---------
#/app/pom.xml
#
#Notice it does not copy the source code yet.
#
#RUN mvn dependency:go-offline -B
#
#This downloads all dependencies defined in pom.xml.
#
#Example:
#
#Spring Boot
#Hibernate
#Jackson
#Validation
#PostgreSQL Driver
#
#The -B flag means batch mode, which is better for automated builds because it suppresses interactive output.
#
#Why copy only pom.xml first?
#
#This is done for Docker caching.
#
#Suppose:
#
#Day 1:
#pom.xml
#src/
#
#Docker executes
#
#COPY pom.xml
#RUN mvn dependency:go-offline
#
#and caches that layer.
#
#Tomorrow you change only:
#
#PatientController.java
#
#The pom.xml hasn't changed, so Docker reuses the cached dependencies instead of downloading everything again. This makes rebuilds much faster.
#
#COPY src ./src
#
#Now the Java source code is copied.
#
#src/
# ↓
#/app/src
#RUN mvn clean package
#
#Runs Maven to build the project.
#
#This performs:
#
#clean
#
#Deletes the old target/ directory.
#
#Then:
#
#compile
#
#Compiles Java files.
#
#Then:
#
#test
#
#Runs tests (unless skipped).
#
#Finally:
#
#package
#
#Creates
#
#target/patient-service-0.0.1-SNAPSHOT.jar
#
#At the end of Stage 1, the container contains:
#
#/app
#    pom.xml
#    src/
#    target/
#        patient-service-0.0.1-SNAPSHOT.jar
#Stage 2: Runtime image
#FROM openjdk:21-jdk AS runner
#
#Now Docker starts a new image.
#
#Everything from the builder stage is discarded unless explicitly copied.
#
#This runtime image contains only:
#
#Java
#Your JAR
#
#No Maven.
#
#No source code.
#
#This keeps the image much smaller.
#
#WORKDIR /app
#
#Again, sets the working directory.
#
#COPY --from=builder ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar
#
#This is the most important line.
#
#It copies the JAR created in the builder stage into the runtime image.
#
#Specifically:
#
#Builder Stage
#----------------------------
#/app/target/patient-service-0.0.1-SNAPSHOT.jar
#
#↓
#
#Runtime Stage
#----------------------------
#/app/app.jar
#
#Since WORKDIR is /app, a slightly clearer version would be:
#
#COPY --from=builder /app/target/patient-service-0.0.1-SNAPSHOT.jar app.jar
#
#Using absolute paths avoids any ambiguity.
#
#EXPOSE 4000
#
#Documents that the application listens on port 4000.
#
#This does not publish the port.
#
#You still need:
#
#docker run -p 4000:4000 image-name
#ENTRYPOINT ["java", "-jar", "app.jar"]
#
#This is the command that runs when the container starts.
#
#Equivalent to:
#
#java -jar app.jar
#
#which launches your Spring Boot application.
#
#Complete flow
#               Local Machine
#            -------------------
#            pom.xml
#            src/
#
#                    │
#                    ▼
#        Stage 1 (Builder)
#   Maven + JDK 21 Container
#   -------------------------
#   Copy pom.xml
#          │
#          ▼
# Download dependencies
#          │
#          ▼
# Copy source code
#          │
#          ▼
# mvn clean package
#          │
#          ▼
# patient-service.jar
#          │
#          ▼
#  Stage 2 (Runtime)
# OpenJDK 21 Container
# -------------------------
# Copy only JAR
#        │
#        ▼
# java -jar app.jar
#Why use two stages?
#
#If you used only one stage with the Maven image:
#
#Maven would remain in the final image.
#Source code would remain in the final image.
#Build caches would remain in the final image.
#The image would be significantly larger.
#
#With a multi-stage build, the final image contains only what is needed to run the application: the JDK and your packaged JAR, making it smaller, faster to distribute, and more secure.



