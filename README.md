# API Cotação – Arquitetura e Estrutura do Projeto

Este repositório contém uma solução **API First** baseada em OpenAPI, organizada em **dois módulos Maven** e preparada para **execução via Docker**, sem necessidade de Java ou Maven instalados no host.

---

## 📦 Estrutura do repositório

```
.
├── api-contracts/
│   ├── pom.xml
│   └── src/main/resources/contracts
│       ├── _compartilhado/v1/schemas.yaml
│       └── cotacoes/v1
│           ├── openapi.yaml
│           └── schemas.yaml
│
├── api-cotacao/
│   ├── pom.xml
│   ├── src/main/java/com/jetnuvem/cotacao
│   │   ├── ApiCotacaoApplication.java
│   │   ├── controller
│   │   ├── service
│   │   └── repository
│   └── src/main/resources
│       ├── application.properties
│       └── static
│           ├── openapi/swagger.yaml
│           └── swagger-ui/
│
├── Dockerfile
├── docker-compose.yaml
└── README.md
```

---

## 🧱 api-contracts

- Define o contrato OpenAPI
- Usa schemas compartilhados
- Gera um bundle único (`cotacao-bundle.yaml`)
- Publica o bundle dentro do JAR

---

## 🚀 api-cotacao

- Consome o bundle do api-contracts
- Gera interfaces e models via OpenAPI Generator
- Implementa Controller / Service / Repository
- Expõe Swagger UI estático

### URLs

- OpenAPI YAML:
  ```
  http://localhost:8080/openapi/swagger.yaml
  ```

- Swagger UI:
  ```
  http://localhost:8080/swagger-ui/index.html
  ```

---

## 🐳 Docker

Build&Run:
```bash
docker compose up -d --build
```

---

## 📌 Conceitos

- API First
- OpenAPI modular
- Bundle versionado
- Swagger UI estático
- Build 100% Docker
