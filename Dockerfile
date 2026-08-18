FROM maven:3-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY src ./src
COPY pom.xml ./
RUN mvn clean install -DskipTests


FROM  eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder ./app/target/java-ecommerce.jar ./
CMD [ "java","-jar","./java-ecommerce.jar" ] 

