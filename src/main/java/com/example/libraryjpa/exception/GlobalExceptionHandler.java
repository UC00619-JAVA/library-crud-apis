package com.example.libraryjpa.exception;

import com.example.libraryjpa.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
// Catches the exceptions thrown by ANY controller.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - the book does not exist
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFound(BookNotFoundException ex) {
        ErrorDetails error = new ErrorDetails(404, "BOOK_NOT_FOUND", ex.getMessage(), Map.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 400 - @Valid failed: one message per invalid field
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fields.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorDetails error = new ErrorDetails(400, "VALIDATION_FAILED", "Invalid request data", fields);
        return ResponseEntity.badRequest().body(error);
    }

    // 400 - the JSON in the body is malformed (extra)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleInvalidJson(HttpMessageNotReadableException ex) {
        ErrorDetails error = new ErrorDetails(400, "INVALID_JSON", "The request body is not valid JSON", Map.of());
        return ResponseEntity.badRequest().body(error);
    }
}
