# Air Quality Monitoring System

## Project Overview
This project monitors real-time and historical air quality data. It includes:
- Multiple **Spring Boot microservices**
- A **React frontend** application
- **RabbitMQ** for messaging
- **Consul** for service discovery and configuration
- **Dockerized deployment** for all services

---

## Prerequisites
Before running the project, ensure you have the following installed:

- **Java 17** or higher
- **Maven**
- **Node.js** (for the frontend)
- **RabbitMQ** (default port: `5672`)
- **Consul** (default port: `8500`)
- **Docker** and **Docker Compose**

---

## Running Locally (Without Docker)

### 1. Start RabbitMQ and Consul
Run RabbitMQ and Consul locally on their default ports:

- Start RabbitMQ:
  ```bash
  rabbitmq-server
### Start Consul:
```bash
consul agent -dev
```

### 2. Build the Microservices
Each microservice is located in its own folder. Run the following for each service:

#### Navigate to the service folder:
```bash
cd path/to/service
```

#### Build the service:
```bash
mvn clean package
```

#### Build the Docker Image:
```bash
docker build -t <service_name> .
```
Replace `<service_name>` with the appropriate name for the service.

#### Service Names Table
| Service                   | Folder Location              | Docker Build Name     |
|---------------------------|------------------------------|-----------------------|
| Gateway Service           | `gateway` folder            | `gateway`            |
| AirQuality Service        | `airquality` folder         | `airquality`         |
| Notification Service      | `airqualitynotificationservice`    | `airqualitynotification` |
| History Service           | `airqualityhistoryservice`         | `airqualityhistory`  |
| Frontend                  | `frontend` folder           | `frontend`           |
| Consul Server             | `docker/consul` folder      | `consul-server`      |

### 3. Run the Microservices
Start each microservice using the following command:

```bash
mvn spring-boot:run
```

#### Running Multiple Instances
To start multiple instances of a service, use a custom port:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=NEW_PORT_NUMBER"
```
Replace `NEW_PORT_NUMBER` with the desired port, e.g., 8082, 8083, etc.

### 4. Run the Frontend Application
#### Navigate to the frontend folder:
```bash
cd frontend
```

#### Install dependencies:
```bash
npm install
```

#### Start the frontend application:
```bash
npm start
```
By default, the frontend will be available at:

```bash
http://localhost:3000
```

### Running with Docker Compose
To build and run all services (microservices, frontend, gateway, RabbitMQ, and Consul) in Docker:

#### Step 1: Build Each Microservice
For each microservice, run the following commands in its respective folder:
```bash
mvn clean package
```
```bash
docker build -t <service_name> .
```
Replace `<service_name>` with the appropriate name for the service (see table above).

#### Step 2: Start All Services with Docker Compose
Navigate to the Docker folder:
```bash
cd path/to/docker
```
Start all services using the custom Docker Compose file:
```bash
docker-compose -f docker-compose-services.yml up --build
```

### Services and Ports
| Service              | Local Port | Docker Container Port |
|----------------------|------------|------------------------|
| Frontend             | 3000       | 80                     |
| AirQuality Service   | 8080       | 8080                   |
| Notification Service | 8081       | 8081                   |
| History Service      | 8087       | 8087                   |
| Gateway       | 8000       | 8000                   |
| RabbitMQ Management  | 15672      | 15672                  |
| Consul UI            | 8500       | 8500                   |

### Accessing the Application
- Frontend: [http://localhost:3000](http://localhost:3000)
- RabbitMQ Management UI: [http://localhost:15672](http://localhost:15672)
    - Default credentials: `guest/guest`
- Consul Dashboard: [http://localhost:8500](http://localhost:8500)

> **Note:** The Gateway Service (http://localhost:8000) is not directly accessible via a browser. It routes requests to other services and acts as an entry point for backend communication.

---

### Troubleshooting

#### 1. RabbitMQ Connection Refused
Ensure RabbitMQ is running on port `5672` locally.
For Docker, ensure the RabbitMQ service name is correct (`rabbitmq-dev`).

#### 2. Consul Unknown Host
Verify Consul is running on port `8500` locally.
For Docker, use the hostname `consul-dev` as configured in `docker-compose-services.yml`.

#### 3. React Frontend 404 on Refresh
If you refresh a route like `/fetch-airquality` and get a 404, ensure your server redirects to `index.html`. 

# Air Quality Monitoring System

## Overview
For my exam, I am developing an air quality monitoring system that provides users with real-time and historical air quality data. The system fetches air quality data from the OpenWeatherMap API and includes the following core features:

- **Real-Time Air Quality Data**: Users can fetch the current air quality for a specified location. Geocoding ensures users can query by city name if coordinates are unknown.
- **Storage of Critical Data**: Air quality values crossing a specified threshold (AQI > 2) are stored in a database for further analysis.
- **Historical Data Visualization**: The system allows users to select a location and view a 5-day graph showing trends of various pollutants. This feature enables users to analyze historical data and monitor air quality changes over time.
- **Database Access**: Users can browse and review stored air quality values retrieved from the database.
- **Geocoding Integration**: Ensures that data can be fetched using city names, enhancing user convenience.

## Technologies and Tools
The system is built with the following technologies:
- **Microservices Architecture**:
    - Each feature is implemented as a separate microservice, ensuring modularity and scalability.
- **RabbitMQ**:
    - Used for asynchronous communication between microservices.
- **Spring Boot**:
    - Framework for building each microservice.
- **Spring Cloud**:
    - Includes features like centralized configuration and service discovery.
- **Consul**:
    - Used for service discovery and health checks, ensuring all services are operational.
- **Docker**:
    - All microservices are containerized for easy deployment and portability.
- **API Gateway with Load Balancer**:
    - Centralized entry point for all services, with load balancing for high availability and scalability.
- **MongoDB**:
    - Database for storing critical air quality data.
- **OpenWeatherMap API**:
    - Source of real-time air quality data.

### User Stories and Implementation
Please read reflection for explanation on notifications
#### 1. Access Real-Time Air Quality Data
**As a user, I want to see the current air quality in my area to inform my activities.**

- **Implementation**:
    - The Air Quality microservice fetches real-time data from the external API and exposes it through a REST endpoint. This data is formatted and passed to the two other services using Eventdriven architectur.
    - The frontend React application displays the current air quality data, which is accessible via a user-friendly interface.

---

#### 2. View Historical Air Quality Trends
**As a user, I want to review historical air quality data to understand pollution trends and assess health impacts.**

- **Implementation**:
    - The AirQualityHistoryService microservice stores historical air quality data recieved from rabbit in MongoDB.
    - The frontend provides a visualization of this data, enabling users to analyze trends over time.

---

#### 3. Receive Notifications for Poor Air Quality
**As a user, I want to be alerted when air quality is hazardous so I can take necessary precautions.**

- **Implementation**:
    - The Air Quality microservice publishes an event via RabbitMQ when air quality exceeds hazardous thresholds.
    - The Notification Service consumes these events and logs notifications to the **console/terminal**.
    - While functional, notifications currently appear in the console/terminal instead of an external system like email, SNS, or a UI alert.

**Future Improvement**:  
The notification feature could be enhanced by integrating AWS SNS or a frontend UI-based alert system to provide more user-friendly and actionable notifications.


## Key Functionalities
1. **Air Quality Data Fetching**:
    - Retrieves current air quality data based on location.
    - Uses geocoding to support location queries by city name.
2. **Data Storage and Alerts**:
    - Automatically stores data exceeding an AQI of 2 in MongoDB.
3. **Historical Data Analysis**:
    - Provides a graphical representation of pollutant trends over 5 days for a selected location.
4. **Database Browsing**:
    - Allows users to view and analyze previously stored air quality records.

## Future Enhancements

- **Advanced Analytics**:
    - Adding features like forecasting or comparisons between locations.
- **Real-Time Notifications**:
    - Integration with AWS SNS for notifying users of critical air quality changes.
- **Scalability**:
    - Expanding the system with more microservices for additional features.

This system aims to provide users with a comprehensive tool to monitor and analyze air quality trends, making it easier to make informed decisions about their environment.
