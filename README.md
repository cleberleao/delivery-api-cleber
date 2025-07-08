# Delivery Tech API

Sistema de delivery desenvolvido com Spring Boot e Java 21.

## 🚀 Tecnologias
- **Java 21 LTS** (versão mais recente)
- Spring Boot 3.4.x
- Spring Web
- Spring Data JPA
- MySQL Database
- Maven
- SpringDoc OpenAPI (Swagger)

## ⚡ Recursos Modernos Utilizados
- Records (Java 14+)
- Text Blocks (Java 15+)
- Pattern Matching (Java 17+)
- Virtual Threads (Java 21)

## 🏃‍♂️ Como executar
1. **Pré-requisitos:** JDK 21 instalado
2. Clone o repositório
3. Execute: `./mvnw spring-boot:run`
4. Acesse: http://localhost:8080/health

## 📋 Endpoints
- GET /health - Status da aplicação (inclui versão Java)
- GET /info - Informações da aplicação
- GET /h2-console - Console do banco H2
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## 🔧 Configuração
- Porta: 8080
- Banco: MySQL (Em Resources arquivo txt para configuração em container usando podman)
- Profile: development

## 👨‍💻 Desenvolvedor
Professor Cleber Leão - Arquitetura de sistemas 
Desenvolvido com JDK 21 e Spring Boot 3.4.x