# Aboriginal Art Gallery API

### Domain:
Art gallery of Aboriginal Art of Australia
#### Bounded Contexts
- Artwork
- Artist
- Exhibition

## Tech Stack
| Technology | Purpose |
|---|---|
| [Kotlin](https://kotlinlang.org/) | Primary language |
| [Spring Boot](https://spring.io/projects/spring-boot) | Application framework |
| [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb) | ODM / Data access |
| [MongoDB](https://www.mongodb.com/) | NoSQL document database |
| [SpringDoc OpenAPI](https://springdoc.org/) | API documentation (Swagger UI) |

## Architecture
<img width="613" height="809" alt="Software architecture diagram" src="https://github.com/user-attachments/assets/9df9ceac-cc44-462c-80ee-c7727dc5e05b" />

## Database

_MongoDB_ has been used with a separate collection for each domain entity.

### Structure
As a NoSQL database, _MongoDB_ does not have enforced relationships or a rigid schema, but an ERD can be used to visualise the structure of the database:

<img width="726" height="412" alt="Database structure diagram" src="https://github.com/user-attachments/assets/a6c605a6-64d5-4831-bc24-68b686226eae" />

## Getting started

### Prerequisites
- Java 21+
- Gradle
- MongoDB running locally on port 27017

### Installation

1. Clone the repository
```bash
   git clone https://github.com/rory-cd/SIT331-5.2HD-BackEnd.git
```

2. Configure environment variables in `application.yml`:
```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://localhost:27017/your-db-name
```

3. Run the application
```bash
   ./gradlew bootRun
```

4. The API will be available at `http://localhost:8080`

5. Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`
