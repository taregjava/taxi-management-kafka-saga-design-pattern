# Taxi Management System with Kafka Integration

This project demonstrates a Taxi Management System built with Spring Boot and Kafka. It handles driver and cab operations using Kafka for communication between services.

## Features
- **Kafka Integration**: Asynchronous event-driven communication.
- **Driver Management**: Add, update, and delete driver details.
- **Cab Management**: Manage cab registration and statuses.
- **Event Handling**: Update driver statuses based on cab events.

## Kafka Topics
- `add-cab-event`: Triggered when a cab is registered.
- `update-driver-event`: Updates driver status based on cab status.

## How It Works
1. **Add Driver**: A driver is added along with cab details.
2. **Trigger Events**: A `CabEvent` is published to the `add-cab-event` topic.
3. **Process Events**: Listeners handle events, process cab details, and update driver statuses.
4. **Driver Status Update**: A `UpdateDriverStatusEvent` is published to `update-driver-event`.

## Prerequisites
- **Kafka**: A running Kafka cluster.
- **Spring Boot**: Java application framework.

## Run the Project
1. Start a Kafka broker and a Zookeeper instance.
2. Update `application.properties` with Kafka broker details.
3. Build the project:
   ```bash
   mvn clean install
