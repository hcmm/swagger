# ========= BUILD =========
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY api-contracts/pom.xml api-contracts/pom.xml
RUN mvn -q -f api-contracts/pom.xml -DskipTests dependency:go-offline

COPY api-contracts api-contracts
RUN mvn -q -f api-contracts/pom.xml -DskipTests install

COPY api-cotacao api-cotacao
RUN mvn -q -f api-cotacao/pom.xml -DskipTests package

# ========= RUNTIME =========
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/api-cotacao/target/api-cotacao-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
