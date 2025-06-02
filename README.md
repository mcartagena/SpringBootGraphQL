# Hands-on GraphQL with Spring Boot 3 and Java 17

This project demonstrates how to build a GraphQL API using Spring Boot 3 and Java 17. It provides a practical example for managing accounts and transactions, showcasing best practices for GraphQL integration in a modern Java backend.

## Features
- GraphQL API for account and transaction management
- Modular schema organization
- Exception handling and request interceptors
- Example domain entities and repositories
- Integration tests

## Prerequisites
- Java 17+
- Maven 3.6+

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd SpringBootGraphQL
   ```
2. **Build the project:**
   ```bash
   ./mvnw clean install
   ```
3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Access GraphQL Playground:**
   Visit [http://localhost:8080/graphiql](http://localhost:8080/graphiql) or the endpoint configured in your application.

## Project Structure
- `src/main/java/com/accounts/` - Main Java source code
- `src/main/resources/graphql/schema/` - GraphQL schema files
- `src/test/java/com/accounts/` - Test cases

## Example GraphQL Query
```graphql
query {
  accounts {
    id
    name
    status
  }
}
```

