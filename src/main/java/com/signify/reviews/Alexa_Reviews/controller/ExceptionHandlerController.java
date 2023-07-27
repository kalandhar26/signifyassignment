package com.signify.reviews.Alexa_Reviews.controller;

import com.signify.reviews.Alexa_Reviews.exceptions.ErrorResponse;
import com.signify.reviews.Alexa_Reviews.exceptions.ErrorSeverity;
import com.signify.reviews.Alexa_Reviews.exceptions.ExceptionDetails;
import com.signify.reviews.Alexa_Reviews.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        HttpStatus resourceNotFoundException = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = getErrorResponse(resourceNotFoundException, ErrorSeverity.critical,ex);
        return new ResponseEntity<>(errorResponse,resourceNotFoundException);
    }

    @ExceptionHandler( value = Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex){
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(ex.getMessage(),internalServerError);
    }

    private ErrorResponse getErrorResponse(HttpStatus resourceNotFoundException, ErrorSeverity errorSeverity, Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .severity(errorSeverity)
                .code("errorParseFailed")
                .message("Unexpected EOF")
                .source("system76")
                .target("system76")
                .build();
        exceptionDetails.setInnerErrors(getInnerErrors(exceptionDetails,ex));

        errorResponse.setError(exceptionDetails);
        return errorResponse;
    }

    private List<ExceptionDetails> getInnerErrors(ExceptionDetails exceptionDetails, Exception ex) {
        List<ExceptionDetails> errorList = new ArrayList<>();
        ExceptionDetails errorDetails =  ExceptionDetails.builder()
                .severity(exceptionDetails.getSeverity())
                .code(exceptionDetails.getCode())
                .message(exceptionDetails.getMessage())
                .source(exceptionDetails.getSource())
                .target(exceptionDetails.getTarget()).build();

        errorList.add(errorDetails);

        return errorList;

    }
}
