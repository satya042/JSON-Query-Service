# 📊 JSON Query Service

A Spring Boot-based backend application that allows dynamic storage and querying of JSON records within named datasets. Supports flexible `Group By` and `Sort By` operations using REST APIs.

---

## 🚀 Features

- Insert JSON records into dynamic datasets
- Group records by any field (`groupBy`)
- Sort records by any field (`sortBy`, `asc` or `desc`)
- JSON stored as raw JSON (text) in relational DB
- RESTful APIs with clean DTO-based responses
- Global exception handling
- Unit and integration test coverage

---

## 🧱 Tech Stack

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- MySQL (can be switched to PostgreSQL)
- JUnit 5 + Mockito

---

## 🔧 Setup Instructions

### 1. Clone the Repository

```
git clone https://github.com/your-username/json-query-service.git
cd json-query-service
```

### 2. Configure MySQL
Make sure your application.properties file contains the following configuration:
```
spring.datasource.url=jdbc:mysql://localhost:3306/jsondb
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 3. Build & Run
```
./mvnw clean install
./mvnw spring-boot:run 
```

## 📡 API Endpoints
### 📝 Insert JSON Record

**POST**  `/api/dataset/{datasetName}/record`

**Request Body**

```
{
  "id": 1,
  "name": "John Doe",
  "age": 30,
  "department": "Engineering"
}
```
**Response**
```
{
  "message": "Record added successfully",
  "dataset": "employee_dataset",
  "recordId": 1
}
```

### 📊 Query JSON Records

**GET**  `/api/dataset/{datasetName}/query?groupBy=department`

**GET** `/api/dataset/{datasetName}/query?sortBy=age&order=asc`
