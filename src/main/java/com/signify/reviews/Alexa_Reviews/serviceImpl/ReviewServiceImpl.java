package com.signify.reviews.Alexa_Reviews.serviceImpl;

import com.signify.reviews.Alexa_Reviews.constants.ReviewConstants;
import com.signify.reviews.Alexa_Reviews.dto.*;
import com.signify.reviews.Alexa_Reviews.entities.Review;
import com.signify.reviews.Alexa_Reviews.exceptions.ResourceNotFoundException;
import com.signify.reviews.Alexa_Reviews.repos.ReviewRepository;
import com.signify.reviews.Alexa_Reviews.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation for managing reviews.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * Save a single review to the database.
     *
     * @param reviewRequestDto The review details to be saved.
     * @return A success message after saving the review.
     */
    @Override
    public String addStoreReview(ReviewRequestDto reviewRequestDto) {
        final String methodName = "ReviewServiceImpl-addStoreReview";
        log.info(ReviewConstants.LOGGER_IN, methodName);
        try {

            Review review = Review.builder()
                    .review(reviewRequestDto.getReview())
                    .author(reviewRequestDto.getAuthor())
                    .review_source(reviewRequestDto.getReview_source())
                    .rating(reviewRequestDto.getRating())
                    .title(reviewRequestDto.getTitle())
                    .product_name(reviewRequestDto.getProduct_name())
                    .reviewed_date(reviewRequestDto.getReviewed_date())
                    .build();

            reviewRepository.save(review);
            log.info("review with id- {} is saved", review.getId());
            log.info(ReviewConstants.LOGGER_OUT, methodName);

            return "Review Added Successfully.!";
        } catch (Exception e) {
            log.info(ReviewConstants.EXCEPTION_LOGGER, e.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Save multiple reviews to the database.
     *
     * @param reviewsListDto The list of reviews to be saved.
     * @return A success message if all reviews are saved; otherwise, a failure message.
     */
    @Override
    public String addStoreReviews(ReviewsListDto reviewsListDto) {
        final String methodName = "ReviewServiceImpl-addStoreReviews";
        log.info(ReviewConstants.LOGGER_IN, methodName);
        try {
            List<Review> reviews = reviewsListDto.getReviews();
            List<Review> savedReviewsList = reviewRepository.saveAll(reviews);
            log.info(" All reviews are saved");
            boolean allSaved = savedReviewsList.stream().map(Review::getId).allMatch(Objects::nonNull);
            log.info(ReviewConstants.LOGGER_OUT, methodName);
            return allSaved ? "Reviews Saved Successfully :)" : "Some reviews could not be saved :(";
        } catch (Exception e) {
            log.info(ReviewConstants.EXCEPTION_LOGGER, e.getMessage());
            return "Failed to save the reviews. Please try again later.";
        }

    }

    /**
     * Filter reviews by date, store source, and rating.
     *
     * @param dateTime The date to filter reviews (optional).
     * @param source   The store source to filter reviews (optional).
     * @param rating   The rating to filter reviews (optional).
     * @return A ReviewsListDto containing the filtered reviews.
     */
    @Override
    public ReviewsListDto filterReviewsByDateOrStoreOrRating(LocalDateTime dateTime, String source, Integer rating) {
        final String methodName = "ReviewServiceImpl-filterReviewsByDateOrStoreOrRating";
        log.info(ReviewConstants.LOGGER_IN, methodName);
        try {
            List<Review> reviewsList = reviewRepository.findAll();
            List<Review> filteredReviewsList = reviewsList.stream().filter(review -> (dateTime == null || review.getReviewed_date().equals(dateTime))).filter(review -> (source == null || review.getReview_source() != null && review.getReview_source().equalsIgnoreCase(source))).filter(review -> (rating == null || Objects.equals(review.getRating(), rating))).toList();
            log.info("Filtered reviews by date, store, and rating.");
            log.info(ReviewConstants.LOGGER_OUT, methodName);
            return ReviewsListDto.builder().reviews(filteredReviewsList).build();
        } catch (Exception e) {
            log.info("Error occurred while filtering reviews: {}", e.getMessage(), e);
            // throw new RuntimeException("Error occurred while filtering reviews", e);
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get average monthly ratings per store.
     *
     * @return A map containing average ratings per store per month.
     */
    @Override
    public AverageMonthlyRatingsPerStoreResponseDto getAverageMonthlyRatingsPerStore() {
        final String methodName = "ReviewServiceImpl-getAverageMonthlyRatingsPerStore";
        log.info(ReviewConstants.LOGGER_IN, methodName);
        try {
            List<Review> reviewsList = reviewRepository.findAll();

            DecimalFormat df = new DecimalFormat("#.###");
            Map<String, Map<String, Double>> averageMonthlyRatingsPerStoreMap = reviewsList.stream()
                    .collect(Collectors.groupingBy(
                            Review::getReview_source,
                            Collectors.groupingBy(
                                    reviewData -> YearMonth.from(reviewData.getReviewed_date()).toString(),
                                    Collectors.collectingAndThen(
                                            Collectors.averagingDouble(Review::getRating),
                                            averageRating -> Double.parseDouble(df.format(averageRating))
                                    ))));
            AverageMonthlyRatingsPerStoreResponseDto averageMonthlyRatings = new AverageMonthlyRatingsPerStoreResponseDto(averageMonthlyRatingsPerStoreMap);
            log.info("Calculated average monthly ratings per store.");
            log.info(ReviewConstants.LOGGER_OUT, methodName);
            return averageMonthlyRatings;
        } catch (Exception e) {
            log.error("Error occurred while calculating average monthly ratings per store: {}", e.getMessage(), e);
            // throw new RuntimeException("Error occurred while calculating average monthly ratings per store", e);
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get average monthly rating for a specific store.
     *
     * @param reviewSource The store source to filter reviews.
     * @return A map containing average ratings per month for the specified store.
     */
    @Override
    public AverageMonthlyRatingForSpecificStoreResponseDto getAverageMonthlyRatingForStore(String reviewSource) {
        final String methodName = "ReviewServiceImpl-getAverageMonthlyRatingForStore";
        log.info(ReviewConstants.LOGGER_IN, methodName);
        try {
            List<Review> reviewsList = reviewRepository.findAll();
            Map<String, Double> AverageMonthlyRatingForSpecificStoreMap = reviewsList.stream()
                    .filter(review -> review.getReview_source().equalsIgnoreCase(reviewSource))
                    .collect(Collectors.groupingBy(
                            review -> YearMonth.from(review.getReviewed_date()).toString(),
                            Collectors.averagingDouble(Review::getRating)
                    ));
            AverageMonthlyRatingForSpecificStoreResponseDto averageMonthlyRatingsOfStore = new AverageMonthlyRatingForSpecificStoreResponseDto(AverageMonthlyRatingForSpecificStoreMap);
            log.info("Calculated average monthly rating for store: {}", reviewSource);
            log.info(ReviewConstants.LOGGER_OUT, methodName);
            return averageMonthlyRatingsOfStore;
        } catch (Exception e) {
            // Log the exception with an appropriate log level (e.g., ERROR)
            log.error("Error occurred while calculating average monthly rating for store: {}", e.getMessage(), e);
            //  throw new RuntimeException("Error occurred while calculating average monthly ratings per store", e);
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get total ratings for each category (5*, 4*, 3*, ...).
     *
     * @return A map containing the count of ratings for each category.
     */
    @Override
    public TotalRatingsByCategoryResponseDto getTotalRatingsByCategory() {
        final String methodName = "ReviewServiceImpl-getTotalRatingsByCategory";
        log.info(ReviewConstants.LOGGER_IN, methodName);
        try {
            List<Review> reviewsList = reviewRepository.findAll();
            Map<Integer, Long> totalRatingsMap = reviewsList.stream()
                    .collect(Collectors.groupingBy(Review::getRating, Collectors.counting()));

            // Fill in the missing rating keys (1 to 5) with zero counts
            for (int i = 1; i <= 5; i++) {
                totalRatingsMap.putIfAbsent(i, 0L);
            }

            TotalRatingsByCategoryResponseDto totalRatingsByCategory = new TotalRatingsByCategoryResponseDto(totalRatingsMap);
            log.info("Calculated total ratings by category.");
            log.info(ReviewConstants.LOGGER_OUT, methodName);
            return totalRatingsByCategory;
        } catch (Exception e) {
            log.error("Error occurred while calculating total ratings by category: {}", e.getMessage(), e);
            // throw new RuntimeException("Error occurred while calculating total ratings by category", e);
            throw new ResourceNotFoundException();
         }
    }
}
