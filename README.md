# Dataset Management API

## Getting Started

### Prerequisites
Ensure you have the following installed:
- **Java 17+**  
- **Spring Boot**  
- **Docker** (for PostgreSQL database)  
- **Gradle**  

### Setup and Running the Project

1. **Clone the repository:**
   ```sh
   git clone https://github.com/MithileshKumar-IITRPR/jsonFilter.git
   cd jsonFilter
   ```

2. **Run PostgreSQL using Docker:**
   ```sh
   docker run --name postgres-db -e POSTGRES_DB=mydatabase -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword -p 5432:5432 -d postgres
   ```

3. **Configure application properties:**
   Ensure the `application.properties` file is set up correctly:
   ```properties
   spring.application.name=jsonData

   # PostgreSQL DataSource Configuration
   spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
   spring.datasource.username=myuser
   spring.datasource.password=mypassword

   # Hibernate Auto DDL Update
   spring.jpa.hibernate.ddl-auto=update

   # Optional Logging
   logging.level.org.springframework=INFO
   ```

4. **Build and run the project:**
   ```sh
   ./gradlew clean build
   ./gradlew bootRun
   ```
   The API will be available at `http://localhost:8080`

---

## API Documentation

### Insert a Record
- **Endpoint:** `POST /record/{datasetName}`
- **Description:** Inserts a new JSON record into the specified dataset.
- **Request Body:**
  ```json
  {
    "id": "123",
    "name": "Sample Record",
    "category": "A"
  }
  ```
- **Response:**
  ```json
  {
    "message": "Record added successfully",
    "dataset": "exampleDataset",
    "recordId": "123"
  }
  ```

### Query Dataset
- **Endpoint:** `GET /query/{datasetName}`
- **Description:** Retrieves dataset records with optional grouping or sorting.
- **Query Parameters:**
  - `groupBy` (optional): Groups records by the specified JSON field.
  - `sortBy` (optional): Sorts records by the specified JSON field.
  - `order` (optional, default: `asc`): Sort order (`asc` or `desc`).
- **Example Requests:**
  - Get all records: `GET /query/exampleDataset`
  - Group by category: `GET /query/exampleDataset?groupBy=category`
  - Sort by name: `GET /query/exampleDataset?sortBy=name&order=desc`
- **Example Response (Grouped by category):**
  ```json
  {
    "groupedRecords": {
      "A": [
        { "id": "123", "name": "Sample Record", "category": "A" }
      ],
      "B": [
        { "id": "124", "name": "Another Record", "category": "B" }
      ]
    }
  }
  ```

---

## Project Structure
```
├── src/main/java/com/example/dataset
│   ├── controller/    # REST controllers
│   ├── service/       # Business logic layer
│   ├── repository/    # Database interaction using JPA
│   ├── model/         # Entity classes for dataset records
├── src/main/resources
│   ├── application.properties  # Configuration files
├── build.gradle  # Gradle dependencies
```

---

## Testing
Run tests using:
```sh
./gradlew test
```

---