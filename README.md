### Events API

## Description

The **Events API** is a REST API developed for event management, following the business rules defined in the project documentation.

The application allows the complete management of events, participants, locations, tickets and registrations, offering operations for creating, querying, updating and removing data through organized endpoints.

The main purpose of the API is to provide a simple and efficient framework for controlling information related to the organization of events.

## Technologies used

* **Java 21**
* **Spring Boot**
* **PostgreSQL**
* **Docker**

## Prerequisites

Before running the project, make sure you have installed:

* Docker
* JDK 21
* PostgreSQL

## How to Run

1. Clone the repository:

```bash
git clone <url-do-repositorio>
```

2. Access the project directory:

```bash
cd events-api
```

3. Run the application using Docker:

```bash
docker compose up
```

The API will be available after service startup.

---


## 🔗 Endpoints documentation
### Participant endpoints

| Method | Endpoint | Description 
| :--- | :--- | :--- 
| **`POST`** | `/api/participants` | Creates a new participants | 
| **`GET`** | `/api/participants` | Lists all available participants | 
| **`GET`** | `/api/participants/{id}` | Lists all available participants using ID | 
| **`PUT`** | `/api/participants/{id}` | Update all available participants using ID | 
| **`DELETE`** | `/api/participants/{id}` | Delete all available participants using ID |

### Event endpoints

| Method | Endpoint | Description 
| :--- | :--- | :--- 
| **`POST`** | `/api/events` | Creates a new events | 
| **`GET`** | `/api/events` | Lists all available events | 
| **`GET`** | `/api/events/{id}` | Lists all available events using ID | 
| **`PUT`** | `/api/events/{id}` | Update all available events using ID | 
| **`DELETE`** | `/api/events/{id}` | Delete all available events using ID |

### Location endpoints

| Method | Endpoint | Description 
| :--- | :--- | :--- 
| **`POST`** | `/api/locations` | Creates a new locations | 
| **`GET`** | `/api/locations` | Lists all available locations | 
| **`GET`** | `/api/locations/{id}` | Lists all available locations using ID | 
| **`PUT`** | `/api/locations/{id}` | Update all available locations using ID | 
| **`DELETE`** | `/api/locations/{id}` | Delete all available locations using ID |

### Ticket endpoints

| Method | Endpoint | Description 
| :--- | :--- | :--- 
| **`POST`** | `/api/tickets` | Creates a new tickets | 
| **`GET`** | `/api/tickets` | Lists all available tickets | 
| **`GET`** | `/api/tickets/{id}` | Lists all available tickets using ID | 
| **`PUT`** | `/api/tickets/{id}` | Update all available tickets using ID | 
| **`DELETE`** | `/api/tickets/{id}` | Delete all available tickets using ID |

### Registration endpoints

| Method | Endpoint | Description 
| :--- | :--- | :--- 
| **`POST`** | `/api/registrations` | Creates a new registrations | 
| **`GET`** | `/api/registrations` | Lists all available registrations | 
| **`GET`** | `/api/registrations/{id}` | Lists all available registrations using ID | 
| **`PUT`** | `/api/registrations/{id}` | Update all available registrations using ID | 
| **`DELETE`** | `/api/registrations/{id}` | Delete all available registrations using ID |



