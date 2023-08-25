#
# Build stage
#
FROM maven:3.8.3-jdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/thunder-0.0.1-SNAPSHOT.jar thunder.jar

# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","thunder.jar"]
