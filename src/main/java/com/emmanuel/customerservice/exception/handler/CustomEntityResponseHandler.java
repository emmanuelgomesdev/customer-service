package com.emmanuel.customerservice.exception.handler;

import com.emmanuel.customerservice.exception.BusinessException;
import com.emmanuel.customerservice.exception.ErrorResponse;
import com.emmanuel.customerservice.exception.ExceptionResponse;
import com.emmanuel.customerservice.exception.FieldErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handlerBusinessException(
            BusinessException ex,
            HttpServletRequest http
    ) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                ex.getErrorResponse().getHttpStatus().value(),
                ex.getErrorResponse().getErrorCode(),
                ex.getErrorResponse().getMessage(),
                http.getRequestURI(),
                List.of());

        return ResponseEntity.status(ex.getErrorResponse().getHttpStatus()).body(response);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        List<FieldErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorResponse(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        ErrorResponse erro = ErrorResponse.CUSTOMER_VALIDATION_ERROR;

        String path = ((ServletWebRequest) request)
                .getRequest()
                .getRequestURI();

        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                erro.getHttpStatus().value(),
                erro.getErrorCode(),
                erro.getMessage(),
                path,
                errors
        );

        return handleExceptionInternal(
                ex,
                response,
                headers,
                erro.getHttpStatus(),
                request
        );
    }
}
