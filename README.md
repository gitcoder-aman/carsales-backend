# Car Sales Analytics & AI Query API

A Spring Boot backend application that allows users to upload car sales data through CSV files, analyze sales trends, and ask questions about the data using natural language powered by AI.

## Live Demo

Base URL:

https://carsales-backend-1fxb.onrender.com

---

## Features

### CSV Data Upload

* Upload car sales records using CSV files
* Bulk data import
* Success and failure tracking
* Validation support

### Sales Analytics

* Yearly sales count
* Monthly sales count by year
* PostgreSQL data storage

### AI-Powered Query System

* Ask questions in natural language
* AI converts user questions into SQL queries
* Executes queries securely
* Returns human-friendly answers
* Supports Hindi and English style questions

Examples:

```text
How many Honda cars are available?

How many cars were sold in 2024?

Model City ke kitne car hai?

How many UPI payments were made?
```

---

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* JDBC Template
* Lombok

### Database

* PostgreSQL
* Neon Database

### AI

* Spring AI
* GEMINI_AI
* gemini-2.5-flash

### Deployment

* Docker
* Render

---

## API Endpoints

### Upload CSV

**POST**

```http
/api/car-sales/upload-csv
```

Request Type:

```text
multipart/form-data
```

Parameter:

| Name | Type          |
| ---- | ------------- |
| file | MultipartFile |

---

### Yearly Sales Count

**GET**

```http
/api/car-sales/yearly-count
```

Example:

```http
GET https://carsales-backend-1fxb.onrender.com/api/car-sales/yearly-count
```

---

### Monthly Sales Count

**GET**

```http
/api/car-sales/monthly-count?year=2024
```

Example:

```http
GET https://carsales-backend-1fxb.onrender.com/api/car-sales/monthly-count?year=2024
```

---

### Ask AI

**POST**

```http
/api/ai/ask
```

Example Request Body:

```text
How many City model cars are available?
```

Example Response:

```text
There are 42 City model cars available in the database.
```

---

## Project Architecture

```text
Client
   |
   v
Spring Boot REST API
   |
   +---- Car Sales Module
   |
   +---- AI Query Module
              |
              v
       SQL Generation
              |
              v
        PostgreSQL
              |
              v
      Human Friendly Response
```

---

## Run Locally

Clone Repository

```bash
git clone https://github.com/gitcoder-aman/carsales-backend.git
```

Move to project directory

```bash
cd carsales-backend
```

Build

```bash
./mvnw clean package
```

Run

```bash
./mvnw spring-boot:run
```

---

## Environment Variables

Production Profile

```properties
DB_URL=
DB_USERNAME=
DB_PASSWORD=
SPRING_PROFILES_ACTIVE=prod
GEMINI_API_KEY=
```

---

## Deployment

* Render (Backend Hosting)
* Neon PostgreSQL (Database)
* Docker (Containerization)
---
## Author

Aman Kumar

Backend Developer | Java | Spring Boot | PostgreSQL | AI Integration
