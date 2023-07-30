package com.signify.reviews.Alexa_Reviews;

import com.signify.reviews.Alexa_Reviews.dto.*;
import com.signify.reviews.Alexa_Reviews.entities.Review;
import com.signify.reviews.Alexa_Reviews.exceptions.ResourceNotFoundException;
import com.signify.reviews.Alexa_Reviews.repos.ReviewRepository;
import com.signify.reviews.Alexa_Reviews.serviceImpl.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link ReviewServiceImpl} to verify its behavior. It includes Junits for Testing
 */
@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;


    /**
     * Test case to verify the successful addition of a single store review.
     */
    @Test
    void testAddStoreReview_Success() {
        // Prepare
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        reviewRequestDto.setReview("Good product");
        reviewRequestDto.setAuthor("John Doe");
        reviewRequestDto.setReview_source("Amazon");
        reviewRequestDto.setRating(5);
        reviewRequestDto.setTitle("Great product");
        reviewRequestDto.setProduct_name("Product XYZ");
        reviewRequestDto.setReviewed_date(LocalDateTime.now());

        // Mock
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review());

        // Execute
        String result = reviewService.addStoreReview(reviewRequestDto);

        // Verify
        assertEquals("Review Added Successfully.!", result);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    /**
     * Test case to verify the failure to add a single store review due to an exception.
     */
    @Test
    void testAddStoreReview_Failure() {
        // Prepare
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        // Mock
        when(reviewRepository.save(any(Review.class))).thenThrow(new RuntimeException());

        // Execute
        Throwable exception = assertThrows(RuntimeException.class,
                () -> reviewService.addStoreReview(reviewRequestDto));

        // Verify
        assertNotNull(exception);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    /**
     * Test case to verify the successful addition of multiple store reviews.
     */
    @Test
    void testAddStoreReviews_Success() {
        // Prepare
        List<Review> reviews = new ArrayList<>();
        Review review1 = Review.builder()
                .id(1L)
                .review("Excellent Product")
                .author("BabaKalandhar")
                .review_source("iTunes")
                .rating(3)
                .title("Novel Book")
                .product_name("Adventures")
                .reviewed_date(LocalDateTime.now()).build();
        Review review2 = Review.builder()
                .id(2L)
                .review("Good Product")
                .author("Sharin")
                .review_source("GooglePlayStore")
                .rating(4)
                .title("Story Book")
                .product_name("Historical")
                .reviewed_date(LocalDateTime.now()).build();
        // Set properties for review1
        reviews.add(review1);
        reviews.add(review2);
        // Add more reviews as needed

        // Mock
        when(reviewRepository.saveAll(anyList())).thenReturn(reviews);

        ReviewsListDto reviewsListDto = new ReviewsListDto();
        reviewsListDto.setReviews(reviews);

        // Execute
        String result = reviewService.addStoreReviews(reviewsListDto);

        // Verify
        assertEquals("Reviews Saved Successfully :)", result);
        verify(reviewRepository, times(1)).saveAll(anyList());
    }

    /**
     * Test case to verify the failure to add multiple store reviews due to an exception.
     */

    @Test
     void testAddStoreReviews_Failure() {
        // Mock the behavior of the reviewRepository.saveAll() method to throw a ResourceNotFoundException
        when(reviewRepository.saveAll(anyList())).thenThrow(new ResourceNotFoundException());

        ReviewsListDto reviewsListDto = new ReviewsListDto();
        reviewsListDto.setReviews(new ArrayList<>());

        // Execute
        Throwable exception = assertThrows(ResourceNotFoundException.class,
                () -> reviewService.addStoreReviews(reviewsListDto));

        // Verify
        assertNotNull(exception);
        verify(reviewRepository, times(1)).saveAll(anyList());
    }

    /**
     * Test case to verify filtering reviews based on various filter combinations when all parameters are null.
     */
    @Test
     void testFilterReviewsByDateOrStoreOrRating_AllNullParameters() {
        // Prepare
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review();
        // Set properties for review1
        reviews.add(review1);
        // Add more reviews as needed

        // Mock
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Execute
        ReviewsListDto result = reviewService.filterReviewsByDateOrStoreOrRating(null, null, null);

        // Verify
        assertEquals(reviews, result.getReviews());
        verify(reviewRepository, times(1)).findAll();
    }

    /**
     * Test case to verify filtering reviews by date parameter.
     */
    @Test
     void testFilterReviewsByDateOrStoreOrRating_WithDateParameter() {
        // Prepare
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review();
        // Set properties for review1
        LocalDateTime dateTime = LocalDateTime.of(2023, 7, 25, 12, 0);
        review1.setReviewed_date(dateTime);
        reviews.add(review1);
        // Add more reviews as needed

        // Mock
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Execute
        ReviewsListDto result = reviewService.filterReviewsByDateOrStoreOrRating(dateTime, null, null);

        // Verify
        assertEquals(Collections.singletonList(review1), result.getReviews());
        verify(reviewRepository, times(1)).findAll();
    }

    /**
     * Test case to verify the ResourceNotFoundException when filtering reviews throws an exception.
     */
    @Test
     void testFilterReviewsByDateOrStoreOrRating_ResourceNotFoundException() {
        // Mock
        when(reviewRepository.findAll()).thenThrow(new RuntimeException());

        // Execute and Verify
        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.filterReviewsByDateOrStoreOrRating(null, null, null));
        verify(reviewRepository, times(1)).findAll();
    }

    /**
     * Test case to verify the successful retrieval of average monthly ratings per store.
     */
    @Test
     void testGetAverageMonthlyRatingsPerStore() {
        // Prepare
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review();
        // Set properties for review1
        review1.setRating(4);
        review1.setReview_source("Amazon");
        review1.setReviewed_date(LocalDateTime.of(2023, 7, 25, 12, 0));
        reviews.add(review1);
        // Add more reviews as needed

        // Mock
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Execute
        AverageMonthlyRatingsPerStoreResponseDto result = reviewService.getAverageMonthlyRatingsPerStore();

        // Verify
        Map<String, Map<String, Double>> expectedMap = new HashMap<>();
        Map<String, Double> innerMap = new HashMap<>();
        innerMap.put("2023-07", 4.0);
        expectedMap.put("Amazon", innerMap);
        assertEquals(expectedMap, result.getAverageMonthlyRatingsPerStore());
        verify(reviewRepository, times(1)).findAll();
    }

    /**
     * Test case to verify the successful retrieval of average monthly rating for a specific store.
     */
    @Test
     void testGetAverageMonthlyRatingForStore_Success() {
        // Prepare test data
        String reviewSource = "Amazon";
        List<Review> reviews = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Review review1 = new Review(1L, "Good product", "John Doe", reviewSource, 5, "Great product", "Product XYZ", now);
        Review review2 = new Review(2L, "Excellent product", "Jane Smith", reviewSource, 4, "Amazing product", "Product ABC", now);
        reviews.add(review1);
        reviews.add(review2);

        // Mocking behavior of reviewRepository.findAll()
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Expected result
        Map<String, Double> expectedAverageRatingsMap = Map.of(YearMonth.from(now).toString(), 4.5);

        // Call the service method
        AverageMonthlyRatingForSpecificStoreResponseDto result = reviewService.getAverageMonthlyRatingForStore(reviewSource);

        // Verify the result
        assertEquals(expectedAverageRatingsMap, result.getAverageMonthlyRatingsForSpecificStore());

        // Verify that reviewRepository.findAll() is called once
        verify(reviewRepository, times(1)).findAll();
    }

    /**
     * Test case to verify the retrieval of average monthly rating for a specific store when no reviews are found.
     */
    @Test
     void testGetAverageMonthlyRatingForStore_NoReviewsFound() {
        // Prepare test data
        String reviewSource = "Amazon";
        List<Review> reviews = new ArrayList<>();

        // Mocking behavior of reviewRepository.findAll()
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Expected result
        Map<String, Double> expectedAverageRatingsMap = Map.of();

        // Call the service method
        AverageMonthlyRatingForSpecificStoreResponseDto result = reviewService.getAverageMonthlyRatingForStore(reviewSource);

        // Verify the result
        assertEquals(expectedAverageRatingsMap, result.getAverageMonthlyRatingsForSpecificStore());

        // Verify that reviewRepository.findAll() is called once
        verify(reviewRepository, times(1)).findAll();
    }

    /**
     * Test case to verify the successful retrieval of total ratings by category.
     */
    @Test
     void testGetTotalRatingsByCategory_Success() {
        // Prepare test data
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review(1L, "Good product", "Baba", "iTunes", 5, "Great product", "Product XYZ", LocalDateTime.now());
        Review review2 = new Review(2L, "Excellent product", "Sharin", "GooglePlayStore", 4, "Amazing product", "Product ABC", LocalDateTime.now());
        Review review3 = new Review(3L, "Average product", "Alavudeen", "GooglePlayStore", 3, "Decent product", "Product DEF", LocalDateTime.now());
        Review review4 = new Review(4L, "Not good product", "Kalandhar", "iTunes", 1, "Bad product", "Product GHI", LocalDateTime.now());
        Review review5 = new Review(5L, "Nice product", "Venus", "iTunes", 5, "Great product", "Product JKL", LocalDateTime.now());
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        reviews.add(review5);

        // Mocking behavior of reviewRepository.findAll()
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Expected result
        Map<Integer, Long> expectedTotalRatingsMap = Map.of(
                1, 1L,
                2, 0L,
                3, 1L,
                4, 1L,
                5, 2L
        );

        // Call the service method
        TotalRatingsByCategoryResponseDto result = reviewService.getTotalRatingsByCategory();

        // Verify the result
        assertEquals(expectedTotalRatingsMap, result.getTotalRatingsByCategory());

        // Verify that reviewRepository.findAll() is called once
        verify(reviewRepository, times(1)).findAll();
    }

    /**
     * Test case to verify the retrieval of total ratings by category when no reviews are found.
     */
    @Test
     void testGetTotalRatingsByCategory_NoReviewsFound() {
        // Prepare test data
        List<Review> reviews = new ArrayList<>();

        // Mocking behavior of reviewRepository.findAll()
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Expected result
        Map<Integer, Long> expectedTotalRatingsMap = Map.of(
                1, 0L,
                2, 0L,
                3, 0L,
                4, 0L,
                5, 0L
        );

        // Call the service method
        TotalRatingsByCategoryResponseDto result = reviewService.getTotalRatingsByCategory();

        // Verify the result
        assertEquals(expectedTotalRatingsMap, result.getTotalRatingsByCategory());

        // Verify that reviewRepository.findAll() is called once
        verify(reviewRepository, times(1)).findAll();
    }

}
