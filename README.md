# Space E-Commerce Microservices Solution

This repository contains a microservices-based solution for an e-commerce platform, structured into four main modules:

- **service-discovery**: Eureka server for service registration and discovery.
- **api-gateway**: API Gateway using Spring Cloud Gateway for routing and centralized access.
- **pagamentos-ms**: Payment microservice for handling payment operations.
- **pedidos-ms**: Order microservice for managing orders and their items.

## Architecture Overview

The solution follows a typical microservices architecture, where each business capability is encapsulated in its own Spring Boot application. Service discovery is handled by Eureka, and all external requests are routed through the API Gateway. The services communicate with each other using REST APIs and leverage Spring Cloud features for resilience and scalability.

### Modules

#### 1. service-discovery

- Implements a Eureka server for service registration and discovery.
- All other services register themselves here for dynamic discovery.

#### 2. api-gateway

- Implements an API Gateway using Spring Cloud Gateway.
- Routes external requests to the appropriate microservices.
- Integrates with Eureka for dynamic routing.

#### 3. pagamentos-ms

- Handles all payment-related operations.
- Communicates with the pedidos-ms to update order status after payment.
- Uses Feign clients for inter-service communication.
- Includes resilience patterns with Resilience4j.

#### 4. pedidos-ms

- Manages orders and their items.
- Exposes endpoints for creating, updating, and querying orders.
- Integrates with pagamentos-ms for payment status updates.

## Running the Solution

**Start the modules in the following order:**

1. **service-discovery**  
   Start the Eureka server first to enable service registration.

2. **api-gateway**  
   Start the API Gateway to expose a unified entry point for all APIs.

3. **pagamentos-ms**  
   Start the payment service so it can register with Eureka and be available for requests.

4. **pedidos-ms**  
   Start the order service last, as it depends on the other services being available.

Each module is a standalone Spring Boot application and can be started using Maven:

```sh
cd <module-directory>
./mvnw spring-boot:run
```

Replace `<module-directory>` with one of: `service-discovery`, `api-gateway`, `pagamentos-ms`, or `pedidos-ms`.

## Configuration

- Each service has its own `application.properties` for configuration.
- Database connection details and Eureka URLs are externalized for flexibility.
- Flyway is used for database migrations in `pagamentos-ms` and `pedidos-ms`.

## Additional Information

- All services are written in Java 17 and use Spring Boot 3.5.x.
- Inter-service communication is handled via REST and Feign clients.
- Resilience4j is used for circuit breaker patterns in the payment service.

---

For more details, refer to the source code of