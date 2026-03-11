# FlightAbyss ✈️

FlightAbyss is a Spring Boot web application for searching flight offers through the Amadeus Flight Offers API. It supports both one-way and round-trip searches, stores search results in a local database, and reuses cached results for repeated searches.

## Features

- Search flights between airports
- Support for one-way and round-trip searches
- Cache flight results in a database
- Reuse cached searches to reduce API calls
- Separate outgoing and return flights
- Display results with Thymeleaf
- Basic error handling for missing flights and API errors

## Tech Stack

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- H2 Database
- Thymeleaf
- Amadeus API
- Jackson

## Project Structure

```text
src/main/java/com/rmartinic/flightabyss
├── FlightAbyssApplication.java   # Application entry point
├── HomeController.java           # Handles web requests
├── FlightService.java            # Business logic and caching
├── AmadeusAuth.java              # Amadeus API authentication
├── Flight.java                   # Entity for search parameters
├── FlightResult.java             # Entity for cached flight offers
├── FlightRepository.java         # Repository for flight searches
├── FlightResultRepository.java   # Repository for cached offers
└── util/
    └── JsonUtil.java             # JSON serialization/deserialization utility
```

## How It Works

1. The user submits a flight search form.
2. The controller sends the request to `FlightService`.
3. The service checks whether the same search already exists in the database.
4. If cached results exist, they are returned from the database.
5. If not, the service calls the Amadeus API.
6. The API response is stored in the database as JSON.
7. The cached JSON is later converted back and displayed in the UI.

## Database Entities

### `Flight`

Stores the search parameters:

- `originAirport`
- `destinationAirport`
- `departureDate`
- `returnDate`
- `numberOfPassengers`
- `currency`

### `FlightResult`

Stores the cached flight offer:

- `flight`
- `offerJson`

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/FlightAbyss.git
cd FlightAbyss
```

### 2. Add Amadeus API credentials

Create or update `src/main/resources/application.properties`:

```properties
amadeus.apiKey=YOUR_API_KEY
amadeus.apiSecret=YOUR_API_SECRET
```

### 3. Run the application

```bash
mvn spring-boot:run
```

or

```bash
./mvnw spring-boot:run
```

### 4. Open in browser

```text
http://localhost:8080
```

## Example Search

- **Origin:** ZAG
- **Destination:** MAD
- **Departure Date:** 2025-07-15
- **Return Date:** 2025-07-22
- **Passengers:** 1
- **Currency:** EUR

## Error Handling

The application handles cases such as:

- No flights found
- Invalid Amadeus credentials
- Invalid user input
- Deserialization errors

## Author

**R. Martinic**  
Computer Science student building a full-stack flight search application using Spring Boot and the Amadeus API.
