package com.emmanuel.customerservice.exception;

public record FieldErrorResponse(
        String field,
        String message
) {
}
