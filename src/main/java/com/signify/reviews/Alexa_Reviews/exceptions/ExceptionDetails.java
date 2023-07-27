package com.signify.reviews.Alexa_Reviews.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "This is Application Specific Exception Object")
public class ExceptionDetails {

    @ApiModelProperty(dataType = "com.signify.reviews.Alexa_Reviews.exceptions.ErrorSeverity", example = "critical",value = "severity")
    private ErrorSeverity severity;

    @ApiModelProperty(dataType = "String", value = "code", example = "errorParseFailed")
    private String code;

    @ApiModelProperty(dataType = "String", value = "message", example = "Unexpected EOF")
    private String message;

    @ApiModelProperty(dataType = "String", value = "source", example = "system76")
    private String source;

    @ApiModelProperty(dataType = "String", value = "target", example = "system76")
    private String target;

    @ApiModelProperty(dataType = "List[com.signify.reviews.Alexa_Reviews.exceptions.ExceptionDetails]", value = "innerErrors", example = "[{\"severity\":\"critical\",\"code\":\"errorParseFailed\"}]")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ExceptionDetails> innerErrors;
}
