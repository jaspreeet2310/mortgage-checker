# Mortgage Checker Project

A Spring Boot REST API for checking mortgage feasibility and monthly installments.

The code covers:
1. [X] Development of two RESTful APIs with Swagger documentation 
2. [X] Custom exception handler for handling InterestRateNotFound based on maturity period
3. [X] Global exception handler for handling all other exceptions
4. [X] Validation of request body
5. [X] SLF4J for Logging in the service class
6. [X] H2 in-memory database with sample loading of data during application startup
7. [X] Java17 features like - Record
8. [X] Unit testing with JUnit and Mockito
9. [X] JavaDoc for documentation
10. [X] Usage of message.properties for internationalization of text and error messages
11. [X] Steps for Docker containerization
12. [X] Steps for Kubernetes deployment

### Assumptions
* The assignment says about the home loan, thus assuming maturity period would always be number of years and not number of months
* There could be multiple interest rates for the same maturity period, implemented the logic to pick the last updated interest rate

P.S - If you don't see any code in the main branch, please check the open Pull Request. Or check the Develop branch:
        develop - https://github.com/jaspreeet2310/mortgage-checker/tree/develop

## Getting Started


To get started with the project, follow these steps:

### Prerequisites

*   Java 17+
*   Maven 3.9+
*   Spring Boot 3.2+
*   Docker (Optional for containerized deployment)
*   jUnit
*   mockito

### Building and Running the Application

1.  Clone the repository using Git: `https://github.com/jaspreeet2310/mortgage-checker.git`
2.  Navigate to the project directory: `cd mortgage-checker`
3.  Build the project using Maven: `mvn clean install`
4.  Run the application using Maven: `mvn spring-boot:run`

### Or using Docker:
1. `docker build -t mortgage-checker:latest .`
2. `docker run -p 8080:8080 mortgage-checker:latest`


## API Documentation

## Swagger Documentation
The API documentation is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) and [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) after running the application.


## Endpoints
*   `GET /api/interest-rates`: Get the list of all available Interest rates and maturity periods
*   `POST /api/mortgage-check`: Returns the feasibility and monthly installments of a mortgage

## Running Tests

Run unit tests:
`mvn test`


## Deployment
### Containerization:
Build and run with Docker:
1. `docker build -t mortgage-checker:latest .`
2. `docker run -p 8080:8080 mortgage-checker:latest`

### How to run the application in Kubernetes
* push the created image to Docker container registry using push command.
* For now the image is already pushed to docker hub : jaspreeet2310/mortgage-checker:latest
* connect to cloud kubernetes cluster
* Deploy the image to kubernetes pod using apply command  `kubectl apply -f deployment.yml`
* Check the status of the pod  `Kubectl get pods`
* Check the service status  `Kubectl get svc`
* Test the application

### How to run the application in Azure Cloud
* Create build pipe line using mvn template or default template
* Define stages for using mvn template, add stages like test,pakage,build image,push image to azure container registry
* Create release pipe line in cloud and deploy the deployment.yaml  kubectl apply -f deployment.yml
* Check the status of the pod  Kubectl get pods
* Test the application


##  Health Check
* `GET /actuator/health`

## H2 database URL
* If running on local, H2 database can be connected at `http://localhost:8080/h2-console`
* `jdbc:h2:mem:mortgagecheckerdb`

## API collection
got to directory ./postman-collection. Here you can see the postman collection

