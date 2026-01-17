# API Cotação – API First com OpenAPI Bundle

Este projeto é uma **API de Cotações** construída seguindo o modelo **API First**, onde o contrato OpenAPI é a fonte da verdade e o código Java é gerado a partir dele.

---

## 🧱 Arquitetura

```
api-contracts
 └─ contracts-bundled/
    └─ cotacoes/v1/cotacao-bundle.yaml
            ↓
api-cotacao
 ├─ OpenAPI Generator (gera interfaces + models)
 ├─ Controller (implementa a interface)
 ├─ Service (regra de negócio)
 ├─ Repository (persistência em memória)
 └─ Swagger UI estático
```

---

## ▶️ Executando a aplicação

```bash
mvn spring-boot:run
```

Aplicação disponível em:
```
http://localhost:8080
```

---

## 📘 OpenAPI & Swagger UI

- OpenAPI YAML:
```
http://localhost:8080/openapi/swagger.yaml
```

- Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔌 Endpoints

### Criar cotação

POST `/cotacoes`

```json
{
  "clienteId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "premioSugerido": {
    "valor": 199.9,
    "moeda": "BRL"
  }
}
```

```bash
curl -X POST http://localhost:8080/cotacoes \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "premioSugerido": {
      "valor": 199.9,
      "moeda": "BRL"
    }
  }'
```

---

### Consultar cotação

GET `/cotacoes/{cotacaoId}`

```bash
curl http://localhost:8080/cotacoes/{cotacaoId}
```

---

## ✅ Status

Projeto funcional com:
- API First
- OpenAPI bundle versionado
- Swagger UI estático
