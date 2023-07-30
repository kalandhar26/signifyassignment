package com.signify.reviews.Alexa_Reviews.controller;

import com.signify.reviews.Alexa_Reviews.dto.*;
import com.signify.reviews.Alexa_Reviews.exceptions.ErrorResponse;
import com.signify.reviews.Alexa_Reviews.service.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Api(value = "Review Controller", tags = "Review API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/addStoreReview")
    @ApiOperation(value = "Add a single review",
            notes = "Add a new review for a store",
            authorizations = {@Authorization(value = "Swagger-Application-Security",
                    scopes = {@AuthorizationScope(scope = "write:review", description = "Write a new review for a store")})})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header", example = "Bearer <your_access_token>", allowEmptyValue = false),
            @ApiImplicitParam(name = "Scope", value = "Scope required: write:review", required = true, dataType = "string", paramType = "header", example = "write:review", allowEmptyValue = false)
    })
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
   public ResponseEntity<String> addStoreReview(@ApiParam(value = "Review data to be added", required = true) @RequestBody ReviewRequestDto reviewRequestDto) {
        return new ResponseEntity<String>(reviewService.addStoreReview(reviewRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/addStoreReviews")
    @ApiOperation(value = "Add multiple reviews",
            notes = "Add multiple reviews for stores",
            authorizations = {@Authorization(value = "Swagger-Application-Security",
                    scopes = {@AuthorizationScope(scope = "write:reviews", description = "Write multiple reviews for stores")})})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
   public ResponseEntity<String> addStoreReviews(@ApiParam(value = "List of review data to be added", required = true) @RequestBody ReviewsListDto reviewsListDto) {
        return new ResponseEntity<String>(reviewService.addStoreReviews(reviewsListDto), HttpStatus.CREATED);
    }

    @GetMapping("/fetchReviews")
    @ApiOperation(value = "Fetch reviews with optional filters",
            notes = "Fetch reviews based on filters like date, store source, and rating",
            authorizations = {@Authorization(value = "Swagger-Application-Security",
                    scopes = {@AuthorizationScope(scope = "read:reviews", description = "Read reviews based on filters")})})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ReviewsListDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    public ResponseEntity<ReviewsListDto> getReviewsByFilters(
            @ApiParam(value = "Filter reviews by date (optional)", required = false) @RequestParam(value = "reviewed_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @ApiParam(value = "Filter reviews by store source (optional)", required = false) @RequestParam(value = "source", required = false) String source,
            @ApiParam(value = "Filter reviews by rating (optional)", required = false) @RequestParam(value = "rating", required = false) Integer rating) {
        return new ResponseEntity<ReviewsListDto>(reviewService.filterReviewsByDateOrStoreOrRating(dateTime, source, rating), HttpStatus.OK);
    }

    @GetMapping("/averageMonthlyRatings")
    @ApiOperation(value = "Get average monthly ratings per store",
            notes = "Get the average monthly ratings for each store",
            authorizations = {@Authorization(value = "Swagger-Application-Security",
                    scopes = {@AuthorizationScope(scope = "read:averageMonthlyRatings", description = "Read average monthly ratings per store")})})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AverageMonthlyRatingsPerStoreResponseDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
   public ResponseEntity<AverageMonthlyRatingsPerStoreResponseDto> getAverageMonthlyRatingsPerStore() {
        return new ResponseEntity<>(reviewService.getAverageMonthlyRatingsPerStore(), HttpStatus.OK);
    }

    @GetMapping("/averageMonthlyRatings/{reviewSource}")
    @ApiOperation(value = "Get average monthly rating for a specific store",
            notes = "Get the average monthly rating for a specific store based on the store source",
            authorizations = {@Authorization(value = "Swagger-Application-Security",
                    scopes = {@AuthorizationScope(scope = "read:averageMonthlyRatingsForStore", description = "Read average monthly rating for a specific store")})})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AverageMonthlyRatingForSpecificStoreResponseDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
   public ResponseEntity<AverageMonthlyRatingForSpecificStoreResponseDto> getAverageMonthlyRatingsForStore(@ApiParam(value = "Store source for which to fetch average monthly rating", required = true) @PathVariable("reviewSource") String reviewSource) {
        return new ResponseEntity<>(reviewService.getAverageMonthlyRatingForStore(reviewSource), HttpStatus.OK);
    }


    @GetMapping("/totalRatingsByCategory")
    @ApiOperation(value = "Get total ratings by rating category",
            notes = "Get the total number of ratings for each rating category (1 to 5)",
            authorizations = {@Authorization(value = "Swagger-Application-Security",
                    scopes = {@AuthorizationScope(scope = "read:totalRatingsByCategory", description = "Read total ratings by rating category")})})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TotalRatingsByCategoryResponseDto.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    public ResponseEntity<TotalRatingsByCategoryResponseDto> getTotalRatingsByCategory() {
        return new ResponseEntity<>(reviewService.getTotalRatingsByCategory(), HttpStatus.OK);
    }
}
