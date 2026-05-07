# 🚨 Real-Time Fraud Detection System  

A **real-time fraud detection platform** built using **Java, Spring Boot, and Apache Kafka** that monitors banking transactions, applies fraud detection rules, and raises alerts for suspicious activities.  
This system mimics **FinTech-grade fraud monitoring** used by modern banking systems to protect customers against fraudulent transactions.  

---

## ⚡ Features  

- **Real-Time Processing** → Detects fraud instantly using event-driven architecture.  
- **Sliding Window Analysis** → Evaluates last 5 transactions with `Deque` for velocity checks.  
- **Rule-Based Engine** → Flags:  
  - >5 transactions within 1 minute.  
  - Transaction amount **3× higher** than historical average.  
  - Suspicious IP/Geo-location ranges.  
- **Event-Driven Alerts** → Publishes fraud alerts to Kafka topic (`fraud.alerts`).  
- **Scalable Microservice Architecture** → Transaction Service, Account Service, Fraud Detection Service.  
- **Extensible** → Ready for ML-driven anomaly detection, customer notifications, and regulatory compliance.  

---

## 🏗️ System Architecture  


- **Transaction Service** → Produces transaction events.  
- **Fraud Detection Service** → Consumes events, applies fraud rules, publishes alerts.  
- **Notification Service** (future) → Sends emails/SMS/alerts for flagged activity.  

---

## 🛠️ Tech Stack  

- **Language**: Java 17  
- **Framework**: Spring Boot  
- **Messaging**: Apache Kafka  
- **Build Tool**: Maven/Gradle  
- **Architecture**: Microservices + Event-driven  

---

## 🚀 Getting Started  

### Prerequisites  
- Java 17+  
- Apache Kafka & Zookeeper running locally  
- Maven/Gradle  

### Run Locally  

```bash
# Clone the repo
git clone https://github.com/arya232004/bankease.git
cd fraud-detection-system

# Start Kafka & Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

# Build and run
mvn spring-boot:run
