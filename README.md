# Real-Time-Fraud-Detection-System
A real-time fraud detection platform built using Java, Spring Boot, and Apache Kafka that monitors banking transactions, applies fraud detection rules, and raises alerts for suspicious activities. This system mimics FinTech-grade fraud monitoring used by modern banking systems to protect customers against fraudulent transactions.
# 🚨 Real-Time Fraud Detection System

A real-time fraud detection platform built using **Java**, **Spring Boot**, and **Apache Kafka** that monitors banking transactions, applies fraud detection rules, and raises alerts for suspicious activities.

This system mimics **FinTech-grade fraud monitoring** used by modern banking systems to protect customers against fraudulent transactions.

---

## ⚡ Features

- **Real-Time Processing** → Detects fraud instantly using event-driven architecture.
- **Sliding Window Analysis** → Evaluates last 5 transactions with Deque for velocity checks.
- **Rule-Based Engine** → Flags:
  - 5 transactions within 1 minute
  - Transaction amount 3× higher than historical average
  - Suspicious IP/Geo-location ranges
- **Event-Driven Alerts** → Publishes fraud alerts to Kafka topic (`fraud.alerts`).
- **Scalable Microservice Architecture** → Transaction Service, Account Service, Fraud Detection Service.
- **Extensible** → Ready for ML-driven anomaly detection, customer notifications, and regulatory compliance.

---

## 🏗️ System Architecture
