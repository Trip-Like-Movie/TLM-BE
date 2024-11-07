package com.TripLikeMovie.backend.global.response.failure;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@NonNullApi
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TripLikeMovieException.class)
    public ResponseEntity<ErrorResponse> TripLikeMovieExceptionHandler(TripLikeMovieException e,
        HttpServletRequest request) {
        log.error("TripLikeMovieException {}", e.getMessage());

        ErrorCode code = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(code.getStatus(), code.getMessage(),
            request.getRequestURL().toString()
        );

        log.info(errorResponse.getPath());

        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
        ConstraintViolationException e, HttpServletRequest request) {
        log.error("Constraint violation error {}", e.getMessage());

        String errorMessages = e.getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));

        String url = request.getRequestURL().toString();

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            errorMessages,
            url
        );

        log.info(errorResponse.getPath());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @SneakyThrows
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {

        log.error("MethodArgumentNotValidException {}", ex.getMessage());

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String url = servletWebRequest.getRequest().getRequestURL().toString();

        Map<String, List<String>> fieldAndErrorMessages = errors.stream()
            .collect(
                Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage()
                            : "입력값에 오류가 있습니다.",
                        Collectors.toList()
                    )
                )
            );

        // message 필드 타입을 Object로 변경하여 다양한 형태 지원
        ErrorResponse errorResponse = new ErrorResponse(status.value(), fieldAndErrorMessages, url);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {

        log.error("HttpRequestMethodNotSupportedException {}", ex.getMessage());

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String url = servletWebRequest.getRequest().getRequestURL().toString();
        ErrorResponse errorResponse = new ErrorResponse(status.value(),
            ErrorCode.METHOD_NOT_ALLOWED.getMessage(), url);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error("HttpMessageNotReadableException: {}", ex.getMessage());

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String url = servletWebRequest.getRequest().getRequestURL().toString();

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "요청 본문을 읽을 수 없습니다. 올바른 JSON 형식으로 요청해 주세요.",
            url
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {

        log.info("ERROR {}", e.getMessage());

        String url = request.getRequestURL().toString();

        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", url);

        return ResponseEntity.status(HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.name())).body(errorResponse);

    }

}
