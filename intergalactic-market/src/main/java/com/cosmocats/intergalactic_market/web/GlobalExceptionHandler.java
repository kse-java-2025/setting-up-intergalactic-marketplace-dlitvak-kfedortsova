package com.cosmocats.intergalactic_market.web;

import com.cosmocats.intergalactic_market.featuretoggle.exception.FeatureToggleNotEnabledException;
import com.cosmocats.intergalactic_market.web.exception.ArgsViolationDetails;
import com.cosmocats.intergalactic_market.service.exception.ProductNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;

import static java.net.URI.create;
import static com.cosmocats.intergalactic_market.util.ProductDetailUtils.getValidationErrorsProblemDetail;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleProductNotFoundException(ProductNotFoundException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Product Not Found");
        problemDetail.setType(create("product-not-found"));
        return problemDetail;
    }

    @ExceptionHandler(FeatureToggleNotEnabledException.class)
    ProblemDetail handleFeatureNotAvailable(FeatureToggleNotEnabledException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(SERVICE_UNAVAILABLE, ex.getMessage());
        problemDetail.setTitle("Feature Disabled");
        problemDetail.setType(create("feature-disabled"));
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ArgsViolationDetails> validationResponse =
                errors.stream().map(err -> ArgsViolationDetails.builder().reason(err.getDefaultMessage()).fieldName(err.getField()).build()).toList();
        return ResponseEntity.status(BAD_REQUEST).body(getValidationErrorsProblemDetail(validationResponse));
    }
}