# Customer Service

Backend desenvolvido em **Java 21** utilizando **Spring Boot** com foco em boas práticas, arquitetura limpa e microsserviços.

Este projeto está sendo utilizado como ambiente de simulação do dia a dia de um Backend Java Enterprise, aplicando tecnologias utilizadas em projetos modernos.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Flyway
- Docker
- Docker Compose
- Maven
- JUnit 5
- Mockito
- Swagger / OpenAPI

---

## 📁 Arquitetura

O projeto segue organização por feature.

```
customer
│
├── application
├── controller
├── domain
├── dto
├── mapper
├── repository
├── service
└── validation
```

Documentações técnicas encontram-se na pasta **docs/**.

---

## ▶️ Executando o projeto

### 1. Clonar o projeto

```bash
git clone <url-do-repositorio>
```

### 2. Subir o banco de dados

```bash
docker compose up
```

### 3. Executar a aplicação

Execute a classe:

```
CustomerServiceApplication
```

ou

```
Run pelo IntelliJ IDEA
```

---

## 📚 API

Após iniciar a aplicação:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🗄️ Banco de Dados

As alterações de banco são controladas através do Flyway.

Todas as migrations encontram-se em:

```
src/main/resources/db/migration
```

---

## 🧪 Testes

Executar:

```bash
./mvnw test
```

Windows:

```powershell
mvnw.cmd test
```

---

## 🐳 Docker

Subir ambiente:

```bash
docker compose up
```

Parar ambiente:

```bash
docker compose down
```

---

## 📌 Roadmap

Próximas implementações:

- [x] Docker
- [x] Docker Compose
- [x] Flyway
- [ ] Redis
- [ ] Kafka
- [ ] AWS
- [ ] GitHub Actions
- [ ] CI/CD
- [ ] Observabilidade
- [ ] Testes de Integração

---

## 👨‍💻 Autor

Emmanuel Gomes