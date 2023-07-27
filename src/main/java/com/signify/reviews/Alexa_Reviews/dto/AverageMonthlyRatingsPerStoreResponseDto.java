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
@ApiModel(description = " stores average monthly ratings of all stores.")
public class AverageMonthlyRatingsPerStoreResponseDto {

    @ApiModelProperty(
            value = "Map with Store Type as key and another Map with Month-Year as key and average rating as value as value.",
            example = "{\"Store1\":{\"2023-01\": 4.5, \"2023-02\": 4.0}, \"Store2\":{\"2023-01\": 3.5, \"2023-02\": 3.0}}",
            dataType = "java.util.Map",
            allowableValues = "NonEmpty Map"
    )
    private Map<String,Map<String,Double>> averageMonthlyRatingsPerStore;
}
