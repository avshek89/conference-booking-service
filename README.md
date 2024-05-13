# Supporting Docs
- [Swagger UI](http://localhost:8080/api/swagger-ui/) GET
- [Swagger Docs](http://localhost:8080/api/v2/api-docs) GET
- [H2 DB Console](http://localhost:8080/api/h2-console) GET


# Application APIs
(Use Swagger UI for more details)
1. Get available conference rooms
2. Book a conference Room

# Conference Room Booking / inquiry APIs
- [List available rooms](http://localhost:8080/api/v1/conference-room/available-rooms?startTime=<startTime>&endTime=<endTime>) GET
- [Book conference room](http://localhost:8080/api/v1/conference-room/booking) POST

# Notions
- Booking is permitted only within specified time frames, with the values adjustable and retrieved from the application's YAML configuration.
- Booking Dates are excluded from the APIs since the objective is to book a conference room for the present date.

# Running the Service
- You can launch the application either via the command line or through an Integrated Development Environment (IDE) like IntelliJ, Eclipse, or Spring Tool Suite (STS), provided it complies with the specified Java and Maven requirements mentioned earlier.
- Navigate to the main class (ConferenceRoomApplication) and select the Run or Debug option.
# Command Line Instructions
- To Build & Test: Execute mvn clean install
- To Run the Application: Use mvn spring-boot:run