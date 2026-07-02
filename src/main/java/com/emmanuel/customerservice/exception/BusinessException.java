package com.emmanuel.customerservice.exception;

public class BusinessException extends BaseException{
    public BusinessException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
