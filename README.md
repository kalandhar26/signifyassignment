# signifyassignment

## Alexa_Reviews Application:
Alexa_Reviews is a Spring Boot application that manages reviews for different stores. It provides endpoints to add single or multiple reviews, filter reviews based on various criteria, and retrieve average monthly ratings for stores. The application uses H2 in-memory database for data storage.

## Getting Started:
To run the Alexa_Reviews application locally, follow these steps:

## Prerequisites:
Java Development Kit (JDK) 11 or higher
Apache Maven
Git

## Clone the Repository:
Clone the repository to your local machine using the following command: git clone https://github.com/your-username/Alexa_Reviews.git

## Build the Application:
Change directory to the cloned repository and build the application using Maven:
cd Alexa_Reviews
mvn clean package

## Run the Application:
After successfully building the application, you can run it using the following command:
mvn spring-boot:run

The application will start on port 8081, and you can access the endpoints using the base URL http://localhost:8081/review.

## API Documentation:
The application provides API documentation using Swagger. After running the application, you can access the Swagger UI at http://localhost:8081/swagger-ui.html. This UI allows you to interact with the API endpoints and view the details of each endpoint.

## Available Endpoints:
POST /review/addStoreReview: Add a single review for a store.

Request body: ReviewRequestDto
Response: String (Success message)
POST /review/addStoreReviews: Add multiple reviews for stores.

Request body: ReviewsListDto
Response: String (Success message)
GET /review/fetchReviews: Fetch reviews with optional filters.

Query parameters:
reviewed_date: Filter reviews by date (optional)
source: Filter reviews by store source (optional)
rating: Filter reviews by rating (optional)
Response: ReviewsListDto
GET /review/averageMonthlyRatings: Get average monthly ratings per store.

Response: AverageMonthlyRatingsPerStoreResponseDto
GET /review/averageMonthlyRatings/{reviewSource}: Get average monthly rating for a specific store.

Path parameter: reviewSource (Store source for which to fetch average monthly rating)
Response: AverageMonthlyRatingForSpecificStoreResponseDto
GET /review/totalRatingsByCategory: Get total ratings by rating category.

Response: TotalRatingsByCategoryResponseDto

## Error Handling
The application provides error handling for common exceptions using Spring's @RestControllerAdvice and @ExceptionHandler. If an error occurs, the API will return an appropriate HTTP status code along with an error response in JSON format.

## Configuration
The application's configuration can be found in the application.properties file. It contains settings for the H2 in-memory database, server port, and other Spring-related properties.

## CORS Configuration
The application is configured to allow cross-origin requests from any domain for the /review/** endpoint. If you plan to deploy this application to a production environment and want to restrict CORS origins, modify the CorsConfiguration class accordingly.

## OpenAPI Specification
The API endpoints are documented using the Springdoc OpenAPI library. 

The YAML representation of the API documentation can be generated using the following Maven command:
mvn clean install

After running the Maven build, the YAML file `swaggerdocument.yaml/swaggerdocument.json` will be available in the `src/main/resources` directory.

To interact with the API documentation in a user-friendly way, you can use the Swagger UI. It can be accessed at:
https://editor.swagger.io/