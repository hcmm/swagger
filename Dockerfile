# ========= BUILD =========
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# 2) Cache: baixar deps do contracts
COPY api-contracts/pom.xml api-contracts/pom.xml
RUN mvn -q -f api-contracts/pom.xml -DskipTests dependency:go-offline

# 3) Build + install do contracts (gera bundle e empacota no JAR)
COPY api-contracts api-contracts
RUN mvn -q -f api-contracts/pom.xml -DskipTests install

# 0) Cache: baixar deps do parent
COPY api-first-spring-parent/pom.xml api-first-spring-parent/pom.xml
RUN mvn -q -f api-first-spring-parent/pom.xml -DskipTests dependency:go-offline

# 1) Instala o parent no repo local do container
COPY api-first-spring-parent api-first-spring-parent
RUN mvn -q -f api-first-spring-parent/pom.xml -DskipTests install


# 4) Build do serviço (vai unpack do jar + gerar interface/models)
COPY api-cotacao api-cotacao
RUN mvn -q -f api-cotacao/pom.xml -DskipTests package

# ========= RUNTIME =========
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/api-cotacao/target/api-cotacao-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
