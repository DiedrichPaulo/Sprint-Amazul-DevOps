FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY NuclearQuest/pom.xml .
COPY NuclearQuest/mvnw .
COPY NuclearQuest/.mvn .mvn

RUN chmod +x mvnw

COPY NuclearQuest/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
