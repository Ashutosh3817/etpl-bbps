# the first stage of our build will use a maven 3.6.1 parent image 
#FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD

# copy the pom and src code to the container 
#COPY ./ ./  

# package our application code for ecs
#RUN mvn clean package 

# the second stage of our build will use open jdk 8 on alpine 3.9 
ARG REPO=855416820330.dkr.ecr.ap-south-1.amazonaws.com

FROM ${REPO}/dev-paybizz-india-ecr:openjdk_8-jre-alpine3.9 

# copy only the artifacts we need from the first stage and discard the rest.
COPY /target/etpl-bbps-0.0.1-SNAPSHOT.jar /etpl-bbps.jar

# set the startup command to execute the jar 
CMD ["java", "-jar", "/etpl-bbps.jar"]
