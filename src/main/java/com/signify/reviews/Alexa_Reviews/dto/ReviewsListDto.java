package com.signify.reviews.Alexa_Reviews.dto;

import com.signify.reviews.Alexa_Reviews.entities.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "DTO for a list of reviews.")
public class ReviewsListDto {

    @ApiModelProperty(value = "List of reviews.", dataType = "java.util.List", allowableValues = "ALLOWLIST", example = "[{\"key1\":\"Value1\",\"key2\":\"Value2\"}]")
    private List<Review> reviews;
}
