package com.signify.reviews.Alexa_Reviews.repos;

import com.signify.reviews.Alexa_Reviews.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
