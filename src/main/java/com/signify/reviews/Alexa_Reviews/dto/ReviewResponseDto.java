package com.signify.reviews.Alexa_Reviews.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "DTO for a single review response.")
public class ReviewResponseDto {

    @ApiModelProperty(value = "The ID of the review.", example = "12345")
    private Long id;

    @ApiModelProperty(value = "The review text.", example = "This product is amazing!")
    private String review;

    @ApiModelProperty(value = "The author of the review.", example = "Baba Kalandhar")
    private String author;

    @ApiModelProperty(value = "The source/store of the review.", example = "Store1")
    private String review_source;

    @ApiModelProperty(value = "The rating of the review.", example = "5")
    private Integer rating;

    @ApiModelProperty(value = "The title of the review.", example = "Great product!")
    private String title;

    @ApiModelProperty(value = "The name of the product.", example = "Product XYZ")
    private String product_name;

    @ApiModelProperty(value = "The date when the review was reviewed.", example = "2023-01-15T12:30:00")
    private LocalDateTime reviewed_date;
}
