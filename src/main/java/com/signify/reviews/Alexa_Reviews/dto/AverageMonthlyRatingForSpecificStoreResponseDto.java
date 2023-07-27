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
@ApiModel(description = "stores average monthly ratings for a specific store.")
public class AverageMonthlyRatingForSpecificStoreResponseDto {

    @ApiModelProperty(value = "Map with Month-Year as key and average rating as value for a specific store.",
            example = "{\"2023-01\": 4.5, \"2023-02\": 4.0}",
            dataType = "java.util.Map",
            allowableValues = "NonEmpty Map")
    private Map<String, Double> averageMonthlyRatingsForSpecificStore;
}
