# Car Sales Analytics API

A Spring Boot REST API for uploading car sales data from CSV files and generating yearly/monthly sales analytics.

## Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL (Neon Database)
* Docker
* Render Deployment
* Maven

## Live API

Base URL:

https://carsales-backend-1fxb.onrender.com

---

## Features

* Upload car sales data using CSV files
* Store data in PostgreSQL
* Get yearly sales count
* Get monthly sales count for a specific year
* RESTful APIs
* Dockerized deployment
* Hosted on Render

---

## API Endpoints

### Upload CSV File

**POST**

```http
/api/car-sales/upload-csv
```

#### Request

Form Data:

| Key  | Type          |
| ---- | ------------- |
| file | MultipartFile |

#### Response

```json
{
  "success": true,
  "message": "All Record Successfully",
  "data": {
    "successCount": 100,
    "failedCount": 0,
    "totalCount": 100
  },
  "status": 200
}
```

---

### Get Yearly Sales Count

**GET**

```http
/api/car-sales/yearly-count
```

#### Example

```http
GET https://carsales-backend-1fxb.onrender.com/api/car-sales/yearly-count
```

#### Response

```json
{
  "success": true,
  "message": "Data Read Successfully",
  "data": [
    {
      "year": 2022,
      "count": 150
    }
  ],
  "status": 200
}
```

---

### Get Monthly Sales Count

**GET**

```http
/api/car-sales/monthly-count?year=2024
```

#### Example

```http
GET https://carsales-backend-1fxb.onrender.com/api/car-sales/monthly-count?year=2024
```

#### Response

```json
{
  "success": true,
  "message": "Monthly data read successfully",
  "data": [
    {
      "month": "JANUARY",
      "count": 25
    }
  ],
  "status": 200
}
```

---

## Run Locally

### Clone Repository

```bash
git clone <your-repository-url>
cd carsales-backend
```

### Configure Database

Update:

```yaml
application-local.yml
```

with your PostgreSQL credentials.

### Build Project

```bash
./mvnw clean package
```

### Run Application

```bash
./mvnw spring-boot:run
```

---

## Deployment

* Backend: Render
* Database: Neon PostgreSQL
* Containerization: Docker

---

## Author

Aman Kumar
