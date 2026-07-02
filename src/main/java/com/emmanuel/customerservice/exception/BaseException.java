package com.emmanuel.customerservice.exception;

public class BaseException extends RuntimeException{

   private final ErrorResponse errorResponse;


    public BaseException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
