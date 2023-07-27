package com.signify.reviews.Alexa_Reviews.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "stores total ratings by category.")
public class TotalRatingsByCategoryResponseDto {

    @ApiModelProperty(
            value = "Map with Category as key (5*, 4*, 3*, ...) and Total Ratings as value.",
            example = "{\"5\": 100, \"4\": 50, \"3\": 20, \"2\": 10, \"1\": 5}",
            dataType = "java.util.Map",
            allowableValues = "NonEmpty Map"
    )
    private Map<Integer, Long> totalRatingsByCategory;
}
