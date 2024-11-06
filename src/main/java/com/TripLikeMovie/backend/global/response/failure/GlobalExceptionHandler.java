package com.TripLikeMovie.backend.global.response.failure;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@NonNullApi
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {

        log.info("ERROR {}", e.getMessage());

        String url = request.getRequestURL().toString();

        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", url);

        return ResponseEntity.status(HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.name())).body(errorResponse);

    }

}
