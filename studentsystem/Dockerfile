FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /workspace/target/*.jar app.jar

ENV SERVER_PORT=9090 \
    SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/student_system \
    SPRING_DATASOURCE_USERNAME=root \
    SPRING_DATASOURCE_PASSWORD=Root@123

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
