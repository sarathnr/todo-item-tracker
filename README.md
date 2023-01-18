## SimpleSystem Todo API

### Overview
The Todo API allows users to create, retrieve and update Todo(s) based on custom requirements.

### Built with

- Spring Boot- Framework
- H2 database - Database
- OpenAPI - API documentation
- Docker - Container

### Installation

1. Clone this repository. 
```
git@github.com:sarathnr/todo-item-tracker.git
```
2. Build the project using maven
```
mvn clean install
```

3. Start the service using docker-compose

```
docker-compose up
```

### API documentation
The API documentation can be found under `/openapi` directory in the project. It is generated using OpenAPI.
After starting the service, the swagger UI can be accessed at following URL 
```
http://localhost:8080/swagger-ui/index.html
```

### Scheduled Job
This service also has a scheduled job - StatusUpdateJob, which runs periodically and updates the status of expired Todo(s) to `PAST_DUE`.
The schedule of the job can be configured in the application.yml file.

### Tests

The below tests are available in the `src/test` folder
- Unit tests of TodoService
- Integration test for TodoController
- JPA test for TodoRepository

### Assumptions

- An item should always have a description
- Due date of an existing item cannot be updated to past date
- Due date of an existing item can be updated to a future date
- Completed date should be empty for an item where it's status is changed to DONE to NOT_DONE


