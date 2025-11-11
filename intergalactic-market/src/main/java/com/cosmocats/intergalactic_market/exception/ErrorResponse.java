package com.cosmocats.intergalactic_market.exception;

public class ErrorResponse {
    int status;
    String error;
    String message;
    String path;

    public ErrorResponse(int value, String badRequest, String fullMessage, String requestURI) {
        this.status = value;
        this.error = badRequest;
        this.message = fullMessage;
        this.path = requestURI;
    }
}
