FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY . .

RUN ./mvnw -q -e -DskipTests clean package

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/mindcare-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
