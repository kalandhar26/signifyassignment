package com.signify.reviews.Alexa_Reviews.exceptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "This is the Response for Custom Exception in Application")
public class ErrorResponse {

    @ApiModelProperty
    private ExceptionDetails error;
}
