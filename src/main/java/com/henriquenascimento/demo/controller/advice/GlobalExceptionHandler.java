package com.henriquenascimento.demo.controller.advice;

import com.henriquenascimento.demo.constant.ErrorConstant;
import com.henriquenascimento.demo.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(final Exception exception,
                                                                  final HttpServletRequest request) {
        log.error("Global Exception: {} | Path: {} | Method: {}",
                exception.getMessage(), request.getRequestURI(), request.getMethod(), exception);
        return new ResponseEntity<>(generateErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorConstant.GLOBAL_EXCEPTION,
                exception.getMessage(),
                request),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(final MethodArgumentNotValidException exception) {
        log.error("Validation Exception: {} | Fields: {}",
                exception.getMessage(), exception.getBindingResult().getFieldErrors());
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomEntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(final CustomEntityNotFoundException exception,
                                                                   final HttpServletRequest request) {
        final String entityName = exception.getEntityName();
        log.error("{}: {} \nEntity: {} \nMethod: {} \nPath: {}",
                exception.getClass().getSimpleName(), exception.getMessage(), entityName, request.getMethod(), request.getRequestURI(), exception);
        return new ResponseEntity<>(generateErrorResponseDTO(
                HttpStatus.NOT_FOUND,
                ErrorConstant.RESOURCE_NOT_FOUND_ERROR_CODE,
                exception.getMessage(),
                request),
                HttpStatus.NOT_FOUND);
    }

    private ErrorResponseDTO generateErrorResponseDTO(final HttpStatus httpStatus,
                                                      final String errorCode,
                                                      final String errorMessage,
                                                      final HttpServletRequest request) {
        return ErrorResponseDTO.builder()
                .timestamp(Instant.now().toEpochMilli())
                .httpStatus(httpStatus.value())
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .method(request.getMethod())
                .path(request.getRequestURI())
                .build();
    }

}
