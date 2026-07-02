# ADR-001 - Identifier Strategy

## Status

Accepted

## Date

2026-06-30

---

# Context

O Customer Service necessita de um identificador único para representar cada cliente.

Esse identificador será utilizado em:

- Banco de Dados
- APIs REST
- Integração entre microsserviços
- Logs
- Eventos (Kafka)
- Auditoria

Era necessário definir qual estratégia de identificação seria utilizada.

---

# Alternatives

## BIGINT AUTO_INCREMENT

### Advantages

- Simples
- Fácil leitura
- Menor espaço em disco

### Disadvantages

- Dependente da sequência do banco
- Pouco indicado para arquiteturas distribuídas
- Expõe quantidade aproximada de registros

---

## UUID

### Advantages

- Identificadores praticamente únicos
- Pode ser gerado pela própria aplicação
- Independente do banco
- Excelente para microsserviços
- Facilita integração entre sistemas

### Disadvantages

- IDs maiores
- Menor legibilidade
- Consome mais espaço que BIGINT

---

# Decision

Foi decidido utilizar UUID como identificador padrão da aplicação.

Essa estratégia está alinhada com arquiteturas modernas baseadas em microsserviços e reduz o acoplamento entre aplicação e banco de dados.

---

# Consequences

## Positivas

- Identificadores únicos
- Independência da sequência do banco
- Melhor integração entre serviços
- Escalabilidade

## Negativas

- IDs maiores
- URLs maiores
- Maior consumo de armazenamento

---

# Implementation

Entity

```java
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
```

Migration

```sql
id UUID NOT NULL
```

---

# References

- RFC 4122 - UUID
- PostgreSQL UUID
- Java UUID API