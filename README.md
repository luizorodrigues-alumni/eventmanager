# 🎟️ EventManager API

## 🚀 Overview
EventManager is a **Spring Boot-based REST API** for event management. It allows users to:
- **Create** events with details like title, location, price, and schedule.
- **Register** for events, including the ability to indicate a referrer.
- **Retrieve** event details and participant rankings.

This project was developed as part of the **NLW Connect** event by **[RocketSeat](https://app.rocketseat.com.br/)**. It follows **RESTful principles** and the **MVC pattern** to ensure scalability and maintainability.

## 🛠️ Technologies Used
- **Java**
- **Spring Boot**
- **Spring Data JPA** (for database interaction)
- **PostgreSQL Database**
- **Lombok** (to reduce boilerplate code)
- **Jakarta Persistence API (JPA)** (for ORM mapping)

## 📌 API Endpoints

### 1. Create a New Event
**Method:** `POST /events`
- **Request Body:**
    ```json
    {
      "title": "Tech Conference 2025",
      "prettyName": "tech-conference",
      "location": "New York, NY",
      "price": 99.99,
      "startDate": "2025-06-10",
      "endDate": "2025-06-12",
      "startTime": "09:00",
      "endTime": "18:00"
    }
    ```
- **Response:** `201 Created`

### 2. Get All Events
**Method:** `GET /events`
- **Response Example:**
    ```json
    [
      {
        "eventId": 1,
        "title": "Tech Conference 2025",
        "prettyName": "tech-conference",
        "location": "New York, NY",
        "price": 99.99,
        "startDate": "2025-06-10",
        "endDate": "2025-06-12"
      }
    ]
    ```

### 3. Get Event by Pretty Name
**Method:** `GET /events/{prettyName}`
- **Response:** `200 OK` or `404 Not Found`

### 4. Register for an Event
**Method:** `POST /subscription/{prettyName}`
- **Request Body:**
    ```json
    {
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
    ```
- **Response:**
    ```json
    {
      "subscriptionId": 1,
      "confirmationUrl": "http://example.com/subscription/tech-conference/1"
    }
    ```

### 5. Register with Referral
**Method:** `POST /subscription/{prettyName}/{userId}`
- **Description:** Allows a user to register someone else, generating referrals for the ranking.

### 6. Get Event Ranking
**Method:** `GET /subscription/{prettyName}/ranking`
- **Response Example:**
    ```json
    [
      {"userId": 1, "userName": "Carlos", "indications": 5},
      {"userId": 2, "userName": "Ana", "indications": 3},
      {"userId": 3, "userName": "Pedro", "indications": 2}
    ]
    ```

### 7. Get User's Position in Ranking
**Method:** `GET /subscription/{prettyName}/ranking/{userId}`
- **Response Example:**
    ```json
    {
      "userId": 2,
      "userName": "Ana",
      "indications": 3,
      "position": 2
    }
    ```

## 📜 REST Principles
1. **Resource-Based:** Each event and subscription has a unique identifier.
2. **Statelessness:** Each request contains all necessary data.
3. **Client-Server Architecture:** The API serves as the backend for various front-end clients.
4. **Uniform Interface:** Standardized HTTP methods and response codes.

## 🔄 MVC Pattern Explanation
- **Model:** Represents entities like `Event`, `User`, and `Subscription`.
- **View:** API responses (JSON) serve as the view for clients.
- **Controller:** `EventController` and `SubscriptionController` handle HTTP requests.
- **Service:** `EventService` and `SubscriptionService` manage business logic.


---

🎉 **This project was built as part of the NLW Connect event by [RocketSeat!](https://app.rocketseat.com.br/)** 🚀

