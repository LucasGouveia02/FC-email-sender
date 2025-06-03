# 📌 Mail Sender Microservice
This is a microservice for mail send and validate account on FoodConnect Mobile app.

---

## 📖 Table of Contents
- [About the Project](#-about-the-project)
- [Technologies Used](#-technologies-used)
- [How to Run](#-how-to-run)
- [Available Endpoints](#-available-endpoints)
- [Request Examples](#-request-examples)
- [Project Structure](#-project-structure)
- [Authors](#-authors)

---

## 🔎 About the Project

The Mail Sender Microservice is responsible for handling email-based account verification and password reset operations for the FoodConnect mobile application.
It performs the following key functions:

- Sends confirmation codes to users during account creation or password recovery.

- Stores temporary validation codes in Redis with expiration for secure short-term access.

- Validates user-provided codes to complete registration or reset flows.

This service plays a vital role in account security and user onboarding within a microservices architecture.



---

## 🛠 Technologies Used

- Java 21  
- Spring Boot 3.4.3
- Spring Web  
- Spring Data JPA  
- MySQL
- Redis
- Docker

---

## 🚀 How to Run

### Prerequisites

- Java 21
- Maven
- A configured database

### Steps

#### Run Redis with Docker
```bash
# Pull Redis image
docker pull redis

# Run Redis container
docker run --name redis-container -p 6379:6379 -d redis
```
#### Run the Microservice
```bash
# Clone the repository
git clone https://github.com/LucasGouveia02/FC-email-sender.git

# Navigate into the project directory
cd FC-email-sender

# Run the project
./mvnw spring-boot:run
```

---

## 🔐 Available Endpoints

| Method| Endpoint         | Description                                                      |
|-------|------------------|------------------------------------------------------------------|
| GET   | cache/key        | Retrieves the value stored in the Redis cache for the given key. |                                                     
| GET   | cache/all-keys   | Returns all keys currently stored in the Redis cache.            |
| POST  | /send-email      | Sends a confirmation code to the specified email if it does not already exist in the database. The code is saved in Redis with a 10-minute expiration. |
| POST  | send-email/resetPassword   | Sends a reset password confirmation code to the specified email and stores it in Redis with a 10-minute expiration, regardless of whether the email exists in the database. |
| POST  | /code-validation | Validates a confirmation code sent by the user (via request body containing email and code). If the code matches and exists in Redis, the account is considered validated and the code is removed from the cache. If the code doesn't match or has expired, an appropriate error response is returned. |

---

## 📦 Request Examples

### Get Key/Value from Cache

```http
GET /cache/key?key=user@example.com
Content-Type: application/json
```

#### Response

```http
{
  "email": "user@example.com",
  "code": "1234",
  "expirationMinutes": 10
}
```

### Get All Key/Values from Cache

```http
GET /cache/all-keys
Content-Type: application/json
```

#### Response

```http
{
  [
    "user@example.com": 1234,
    "admin@example.com": 4321
  ]
}
```

### Send email for Validate Account

```http
POST /send-email?email=user@example.com
Content-Type: application/json
```

#### Response

```http
HTTP/1.1 201 Created
Confirmation code send to user@example.com with the code 1234
```

### Send email for Reset Password

```http
POST /send-email/resetPassword?email=user@example.com
Content-Type: application/json
```

#### Response

```http
HTTP/1.1 201 Created
Confirmation code send to user@example.com.
```

### Validate Confirmation Code

```http
POST /code-validation
Content-Type: application/json

{
  "email": "user@example.com",
  "code": "1234"
}
```

#### Response

```http
HTTP/1.1 200 OK
The account user@example.com has been validated with the code 1234
```

```http
HTTP/1.1 400 Bad Request
The provided code is not valid.
```

```http
HTTP/1.1 404 Not Found
Validation code has expired.
```

---

## 📁 Project Structure

```css
src
├── main
│   ├── java
│   │   └── com.br.foodconnect
│   │       ├── config
|   |       ├── controller
│   │       ├── dto
│   │       ├── repository
│   │       ├── service
│   │       └── util
│   └── resources
|   |    └── static
```

---

## 👤 Authors

**Jefferson Cavalcante**

🐙 GitHub: [@jcavalcantee](https://github.com/jcavalcantee)

💼 LinkedIn: [Jefferson Cavalcante](https://www.linkedin.com/in/jeffersoncavalcante8 )

**Gabriel Santos Attuy**

🐙 GitHub: [@Gabs-Attuy](https://github.com/Gabs-Attuy) 

💼 LinkedIn: [Gabriel Attuy](https://www.linkedin.com/in/gabriel-attuy-197010265)
