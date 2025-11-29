package com.cosmocats.intergalactic_market.util;

import java.net.URI;
import java.util.List;

import com.cosmocats.intergalactic_market.web.exception.ArgsViolationDetails;
import lombok.experimental.UtilityClass;
import org.springframework.http.ProblemDetail;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@UtilityClass
public class ProductDetailUtils {

    public static ProblemDetail getValidationErrorsProblemDetail(List<ArgsViolationDetails> validationResponse) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Request validation failed");
        problemDetail.setType(URI.create("urn:problem-type:validation-error"));
        problemDetail.setTitle("Field Validation Exception");
        problemDetail.setProperty("invalidParams", validationResponse);
        return problemDetail;
    }
}