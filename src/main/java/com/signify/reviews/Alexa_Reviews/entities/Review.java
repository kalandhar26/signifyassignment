package com.signify.reviews.Alexa_Reviews.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100000)
    private String review;

    private String author;

    private String review_source;

    private Integer rating;

    private String title;

    private String product_name;

    private LocalDateTime reviewed_date;
}
