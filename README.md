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


## Test APIs on any 3rd Party Browser

=============================== Add Single Store Review =================================

POST: https://reviews-service.onrender.com/review/addStoreReview

Request Body:

{
"review": "Pero deberia de poder cambiarle el idioma a alexa",
"author": "WarcryxD",
"review_source": "iTunes",
"rating": 4,
"title": "Excelente",
"product_name": "Amazon Alexa",
"reviewed_date": "2018-01-12T02:27:03.000Z"
}

Success Response: Review Added Successfully.!

Failure Response :

{
"error": {
"severity": "critical",
"code": "errorParseFailed",
"message": "Unexpected EOF",
"source": "system76",
"target": "system76",
"innerErrors": [
{
"severity": "critical",
"code": "errorParseFailed"
}
]
}
}


=============================== Add Multiple Store Reviews =================================
POST : https://reviews-service.onrender.com/review/addStoreReviews


Request Body :

{
"reviews": [
{
"review": "Pero deberia de poder cambiarle el idioma a alexa",
"author": "WarcryxD",
"review_source": "iTunes",
"rating": 4,
"title": "Excelente",
"product_name": "Amazon Alexa",
"reviewed_date": "2018-01-12T02:27:03.000Z"
},
{
"review": "Cannot fix connection glitches without this-also fix connection glitches \n\nSmart things sees my light and Alexa doesn’t :(\n\n*update new devices “unresponsive” each day...they are working fine in SmartThings. No way to delete disabled devices. Very poor.",
"author": "Ranchorat",
"review_source": "iTunes",
"rating": 1,
"title": "Need to be able to delete devices",
"product_name": "Amazon Alexa",
"reviewed_date": "2017-12-06T13:06:33.000Z"
},
{
"review": "After typing in the log in screen and submit, jump back to the login screen.",
"author": "omgzero",
"review_source": "iTunes",
"rating": 1,
"title": "Can’t log it",
"product_name": "Amazon Alexa",
"reviewed_date": "2017-12-06T14:48:03.000Z"
}
]
}


Success Response : Reviews Saved Successfully :)

Failure Response :

{
"error": {
"severity": "critical",
"code": "errorParseFailed",
"message": "Unexpected EOF",
"source": "system76",
"target": "system76",
"innerErrors": [
{
"severity": "critical",
"code": "errorParseFailed"
}
]
}
}


=============================== fetch store Reviews using Filters as optional parameters =================================

GET : https://reviews-service.onrender.com/review/fetchReviews

Success Response : fetches All Reviews

GET : https://reviews-service.onrender.com/review/fetchReviews?rating=4

Success Response : fetches All Reviews whose rating is 4.

GET : https://reviews-service.onrender.com/review/fetchReviews?source=iTunes

Success Response : fetches All Reviews whose source is iTunes.

GET : https://reviews-service.onrender.com/review/fetchReviews?reviewed_date=2018-01-12T02:27:03

Success Response : fetches All Reviews whose reviewed date is 2018-01-12T02:27:03.

We can use some combinations as well

like rating and source
rating and date
source and date
rating , source and date.

https://reviews-service.onrender.com/review/fetchReviews?rating=4&source=iTunes
https://reviews-service.onrender.com/review/fetchReviews?rating=4&reviewed_date=2018-01-12T02:27:03
https://reviews-service.onrender.com/review/fetchReviews?source=iTunes&reviewed_date=2018-01-12T02:27:03
https://reviews-service.onrender.com/review/fetchReviews?rating=4&source=iTunes&reviewed_date=2018-01-12T02:27:03

=============================== fetch average Monthly Ratings of a Specific store =================================

GET : https://reviews-service.onrender.com/review/averageMonthlyRatings/itunes
GET : https://reviews-service.onrender.com/review/averageMonthlyRatings/GooglePlayStore

Success Response:

{
"averageMonthlyRatingsForSpecificStore": {
"2018-01": 1.7636363636363637,
"2018-02": 1.7316017316017316,
"2017-12": 1.707395498392283
}
}


Failure Response:

{
"error": {
"severity": "critical",
"code": "errorParseFailed",
"message": "Unexpected EOF",
"source": "system76",
"target": "system76",
"innerErrors": [
{
"severity": "critical",
"code": "errorParseFailed"
}
]
}
}


=============================== fetch average Monthly Ratings of all stores =================================

GET : https://reviews-service.onrender.com/review/averageMonthlyRatings

Success Response:

{
"averageMonthlyRatingsPerStore": {
"iTunes": {
"2018-01": 1.764,
"2018-02": 1.732,
"2017-12": 1.707
},
"GooglePlayStore": {
"2017-05": 2.004,
"2017-06": 2.376,
"2017-10": 2.846,
"2018-01": 2.939,
"2017-11": 2.565,
"2018-02": 2.987,
"2017-12": 2.863,
"2017-07": 2.559,
"2017-08": 2.917,
"2017-09": 2.647
}
}
}

Failure Response:

{
"error": {
"severity": "critical",
"code": "errorParseFailed",
"message": "Unexpected EOF",
"source": "system76",
"target": "system76",
"innerErrors": [
{
"severity": "critical",
"code": "errorParseFailed"
}
]
}
}


=============================== fetch total ratings by Category =================================

GET : https://reviews-service.onrender.com/review/totalRatingsByCategory

Success Response :

{
"totalRatingsByCategory": {
"1": 2520,
"2": 928,
"3": 743,
"4": 482,
"5": 1437
}
}

Failure Response :

{
"error": {
"severity": "critical",
"code": "errorParseFailed",
"message": "Unexpected EOF",
"source": "system76",
"target": "system76",
"innerErrors": [
{
"severity": "critical",
"code": "errorParseFailed"
}
]
}
}

