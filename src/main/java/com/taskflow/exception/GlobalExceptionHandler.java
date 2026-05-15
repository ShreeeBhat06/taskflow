package com.taskflow.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.taskflow.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	//Handles the custom 404 
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleNotFound(TaskNotFoundException ex)
	{
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse.failure(ex.getMessage()));
	}
	
	// Handles @Valid failures (bad input)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(errors));
    }

    // Catch-all for anything unexpected
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("Something went wrong: " + ex.getMessage()));
    }
}
