package com.emmanuel.customerservice.exception;

import org.springframework.http.HttpStatus;

public enum ErrorResponse {

    CUSTOMER_NOT_FOUND("CUSTOMER-001", "Customer not found", HttpStatus.NOT_FOUND),
    CUSTOMER_ALREADY_EXISTS("CUSTOMER-002", "Customer already exists", HttpStatus.CONFLICT),
    CUSTOMER_INVALID_ID("CUSTOMER-003", "Invalid customer id", HttpStatus.BAD_REQUEST),
    CUSTOMER_INVALID_REQUEST("CUSTOMER-004", "Invalid request", HttpStatus.BAD_REQUEST),
    CUSTOMER_VALIDATION_ERROR("CUSTOMER-005", "Validation error", HttpStatus.BAD_REQUEST),
    CUSTOMER_REQUIRED_FIELD("CUSTOMER-006", "Required field", HttpStatus.BAD_REQUEST),
    CUSTOMER_UPDATE_ERROR("CUSTOMER-007", "Error updating customer", HttpStatus.UNPROCESSABLE_ENTITY),
    CUSTOMER_DELETE_ERROR("CUSTOMER-008", "Error deleting customer", HttpStatus.UNPROCESSABLE_ENTITY),
    CUSTOMER_ALREADY_INACTIVE("CUSTOMER-009", "Customer already inactive", HttpStatus.CONFLICT),
    CUSTOMER_ALREADY_ACTIVE("CUSTOMER-010",   "Customer already active", HttpStatus.CONFLICT),;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;


    ErrorResponse(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
