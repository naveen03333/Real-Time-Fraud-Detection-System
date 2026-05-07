# BankEase — Microservices Banking System 🚀

A simple microservices-style banking simulation built with **Spring Boot**, **Kafka**, **MySQL**, and **JWT-based authentication**.\
Initially organized as a **single repo with multiple modules** so services can run independently from the same codebase.

---

## 📌 Overview

BankEase models a small banking platform with **3 microservices**:

1. **Account Service**

   - Create accounts
   - Get account details
   - Store in MySQL
   - Publish `account.created` events to Kafka

2. **Transaction Service**

   - Handle deposits, withdrawals, transfers
   - Store transactions in MySQL
   - Consume Kafka events

3. **Notification Service**

   - Simulate email/SMS alerts
   - Listen to Kafka events

---

## 🛠 Tech Stack

- **Java 17+ / Spring Boot**
- Spring Web, Spring Data JPA
- Spring Security (JWT)
- Spring for Apache Kafka
- MySQL
- Lombok
- Docker Compose (MySQL + Kafka + Zookeeper + optional tools)
- Maven (multi-module)
- Testcontainers (optional for integration tests)

---

## 🔧 Step 1 — Spring Initializr Dependencies (per microservice module)

When you create each microservice module (or start from Spring Initializr), include these dependencies:

**Required**

- Spring Web
- Spring Data JPA
- MySQL Driver
- Spring Boot DevTools
- Lombok
- Spring for Apache Kafka (`spring-kafka`)
- Spring Security (to enable JWT-based auth)

**Optional / Recommended**

- Spring Boot Actuator (monitoring)
- Spring Validation (`spring-boot-starter-validation`)
- Spring Boot Test / Testcontainers (for integration tests)

**Example Spring Initializr (UI) options**

- Project: Maven
- Language: Java
- Spring Boot: `3.x` (or latest stable)
- Group: `com.bankease`
- Artifact: `account-service` / `transaction-service` / `notification-service`
- Packaging: `jar`
- Java: `17`
- Dependencies: pick the list above

---

## 📂 Repo Layout (Maven Multi-Module)

```
bankease/
├─ pom.xml                 # Parent POM (defines modules)
├─ docker-compose.yml
├─ bank-common/            # shared DTOs / events (optional)
│  ├─ pom.xml
│  └─ src/...
├─ account-service/
│  ├─ pom.xml
│  └─ src/...
├─ transaction-service/
│  ├─ pom.xml
│  └─ src/...
├─ notification-service/
│  ├─ pom.xml
│  └─ src/...
└─ README.md
```

> Tip: create a `bank-common` module to share event DTOs and constants (topic names, event versions) between services.

---

## 🏗 Infrastructure

This section collects the infra pieces you'll typically run locally for development. It includes a `docker-compose.yml` example and commands you can use to create Kafka topics and optional tooling (MailHog, Kafdrop, Jaeger, Prometheus).

### What this infra provides (recommended for local dev)

- Zookeeper + Kafka broker (message bus)
- MySQL database
- MailHog (SMTP UI) — simulate and view emails
- Kafdrop (Kafka web UI) — inspect topics & messages
- (Optional) Jaeger for tracing, Prometheus + Grafana for metrics

> For CI / tests use Testcontainers instead of this full stack when possible.

### Example `docker-compose.yml` (development)

```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:7.4.0
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENERS: 'PLAINTEXT://0.0.0.0:9092'
      # If Docker is running on the same host you use to connect, advertise localhost
      KAFKA_ADVERTISED_LISTENERS: 'PLAINTEXT://localhost:9092'
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    ports:
      - '9092:9092'

  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: bankease
      MYSQL_USER: bankuser
      MYSQL_PASSWORD: bankpass
    ports:
      - '3306:3306'
    healthcheck:
      test: ['CMD', 'mysqladmin', 'ping', '-h', 'localhost']
      interval: 10s
      timeout: 5s
      retries: 5
    volumes:
      - mysql-data:/var/lib/mysql

  mailhog:
    image: mailhog/mailhog
    ports:
      - '1025:1025'   # SMTP
      - '8025:8025'   # Web UI

  kafdrop:
    image: obsidiandynamics/kafdrop:latest
    depends_on:
      - kafka
    environment:
      KAFKA_BROKERCONNECT: 'kafka:9092'
      JVM_OPTS: '-Xms32M -Xmx64M'
    ports:
      - '9000:9000'

volumes:
  mysql-data:
```

**Notes**

- `KAFKA_ADVERTISED_LISTENERS` may need to change if Docker runs on WSL2, remote hosts, or CI. For WSL2 you might expose the host IP instead of `localhost`.
- `kafdrop` is optional but handy for inspecting topic messages and consumer groups.
- `mailhog` exposes a web UI at `http://localhost:8025` for viewing outgoing emails (useful for Notification Service testing).

### Creating Kafka topics (quick)

You can create topics with the Kafka CLI from inside the Kafka container (example for the compose above):

```bash
# Get a shell in the kafka container (replace container name if different)
docker-compose exec kafka bash

# Inside the container (creates topic if not exists):
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic account.created
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic transaction.created
```

If you prefer one-liners from the host (works with the example compose where kafka is mapped to localhost:9092):

```bash
docker exec -it $(docker ps -qf "ancestor=confluentinc/cp-kafka:7.4.0") kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic account.created --if-not-exists
```

> You can also let your services auto-create topics (Spring Kafka can auto-create when `spring.kafka.admin.auto-create` is enabled), but explicit topic creation is recommended for stable configs in dev and prod.

### Topic configuration recommendations (dev vs prod)

- **Partitions**: start with `3` for dev (enables parallelism). In prod, choose partitions based on throughput and consumer parallelism.
- **Replication factor**: `1` for local dev; `>=3` for a production cluster.
- **Retention**: adjust `retention.ms` if you need messages to stick around.

### Optional local tooling

- **Schema Registry** (if you use Avro/Confluent schemas)
- **Kafkacat / kcat** (CLI for producing/consuming messages)
- **Prometheus + Grafana** for metrics (expose actuator endpoints and scrape)
- **Jaeger** for tracing (if you add OpenTelemetry)

---

## 🚀 Getting Started (Local)

### **Prerequisites**

- Java 17+
- Maven
- Docker & Docker Compose

---

### **1️⃣ Clone the Repo**

```bash
git clone <your-repo-url>
cd bankease
```

---

### **2️⃣ Start Infrastructure (MySQL + Zookeeper + Kafka + extras)**

```bash
docker-compose up -d
```

---

## ⚙️ Configure Each Service

Example `account-service/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/bankease
    username: bankuser
    password: bankpass
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      group-id: account-service-group
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer

jwt:
  secret: change_this_secret_to_env_var
  expiration-ms: 3600000
```

---

## 📡 API Quick Reference (Account Service)

### **Create Account**

```
POST /api/v1/accounts
Content-Type: application/json

{
  "ownerName": "Jane Doe",
  "email": "jane@example.com",
  "initialDeposit": 1000.00
}
```

**Behavior**

- Persists account in MySQL
- Publishes `account.created` event to Kafka
- Optionally returns a JWT for the new user (dev mode)

### **Get Account**

```
GET /api/v1/accounts/{id}
Authorization: Bearer <jwt>
```

---

## 📢 Event Contracts

### **Topic:** `account.created`

```json
{
  "accountId": "uuid-or-long",
  "ownerName": "Jane Doe",
  "email": "jane@example.com",
  "createdAt": "2025-08-12T12:34:56Z",
  "initialDeposit": 1000.0,
  "version": "v1"
}
```

### **Topic:** `transaction.created`

```json
{
  "transactionId": "uuid",
  "accountId": "uuid-or-long",
  "type": "DEPOSIT",
  "amount": 1000.0,
  "timestamp": "2025-08-12T12:35:00Z",
  "metadata": { "note": "initial deposit" },
  "version": "v1"
}
```

> Always include a `version` field in events so you can evolve schemas safely.

---

## 🔄 Flow Diagram (text)

1. **Client** → `POST /accounts` (Account Service)
2. Account Service → **MySQL** (save account)
3. Account Service → **Kafka** (`account.created` event)
4. Transaction Service → listens to `account.created` → create opening transaction
5. Notification Service → listens to both `account.created` & `transaction.created` → send simulated alert

---

## 💡 Development Tips

- Use **Testcontainers** for reproducible Kafka & MySQL in tests
- Keep event payloads **small and versioned**
- Use a **shared **``** module** for common DTOs and events
- In dev you can use one DB for all services; in production prefer **separate DB per service**
- Use idempotency keys in consumers to prevent duplicate processing

---

## 🧪 Running Tests

Unit tests:

```bash
mvn test
```

Integration tests with Testcontainers:

```xml
<dependency>
  <groupId>org.testcontainers</groupId>
  <artifactId>testcontainers</artifactId>
  <version>1.18.3</version>
  <scope>test</scope>
</dependency>
```

---

## 🗺 Roadmap

-

---

## 🐞 Troubleshooting

| Issue                    | Solution                                                                   |
| ------------------------ | -------------------------------------------------------------------------- |
| Kafka not connecting     | Check `KAFKA_ADVERTISED_LISTENERS` and that port `9092` is reachable       |
| MySQL connection refused | Wait for container readiness (`healthcheck`) or check credentials          |
| Events not consumed      | Verify consumer `group-id`, topic subscription, and that serializers match |

---

## 🤝 Contributing

1. Open an **issue** for bugs or feature ideas
2. Create a **branch** (`feat/xxx` or `fix/xxx`)
3. Submit a **PR** with tests for new behavior

---

## 📜 License

MIT — free to use and modify.

---

## 🔭 Next steps (scaffold suggestions)

If you want, I can
