package com.signify.reviews.Alexa_Reviews.service;
import com.signify.reviews.Alexa_Reviews.dto.*;
import com.signify.reviews.Alexa_Reviews.entities.Review;
import com.signify.reviews.Alexa_Reviews.repos.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Service
public interface ReviewService {

    public String addStoreReview(ReviewRequestDto reviewRequestDto);

    public String addStoreReviews(ReviewsListDto reviewsListDto);

    public ReviewsListDto filterReviewsByDateOrStoreOrRating(LocalDateTime dateTime, String source, Integer rating);

    // Map Stores store_type as Key and Another Map as Value
    // Another Map stores Month-Year as Key and Average Rating as Value
    public AverageMonthlyRatingsPerStoreResponseDto getAverageMonthlyRatingsPerStore();

   // Map stores Month-Year as Key and Average Rating as Value for a Specific Store
    public AverageMonthlyRatingForSpecificStoreResponseDto getAverageMonthlyRatingForStore(String reviewSource);

    // Map Stores Category as Key (5*,4*,3*...) and Total Ratings as Value.
    public TotalRatingsByCategoryResponseDto getTotalRatingsByCategory();

}
