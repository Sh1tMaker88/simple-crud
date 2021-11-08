FROM maven:3.8.3-jdk-11 AS build
WORKDIR /usr/src/app
COPY . ./
COPY src ./src
RUN mvn clean package -Dmaven.test.skip

FROM openjdk:11-jre-slim
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/target/simple-crud-0.0.1-SNAPSHOT.jar ./simple-crud.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./simple-crud.jar"]
