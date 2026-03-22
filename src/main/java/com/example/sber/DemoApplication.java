package com.example.sber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.ConstraintViolationException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RestControllerAdvice
	public static class ApiExceptionHandler {
		@ExceptionHandler(NoSuchElementException.class)
		public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException ex) {
			return build(HttpStatus.NOT_FOUND, "Resource not found");
		}

		@ExceptionHandler({
				IllegalArgumentException.class,
				MethodArgumentNotValidException.class,
				ConstraintViolationException.class,
				ResponseStatusException.class
		})
		public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex) {
			if (ex instanceof MethodArgumentNotValidException validationEx) {
				String message = validationEx.getBindingResult().getFieldErrors().stream()
						.map(FieldError::getDefaultMessage)
						.findFirst()
						.orElse("Validation failed");
				return build(HttpStatus.BAD_REQUEST, message);
			}
			if (ex instanceof ResponseStatusException statusEx && statusEx.getStatusCode().is4xxClientError()) {
				return build(HttpStatus.valueOf(statusEx.getStatusCode().value()), statusEx.getReason());
			}
			return build(HttpStatus.BAD_REQUEST, ex.getMessage() != null ? ex.getMessage() : "Bad request");
		}

		@ExceptionHandler(Exception.class)
		public ResponseEntity<Map<String, Object>> handleUnhandled(Exception ex) {
			return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
		}

		private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
			Map<String, Object> body = new HashMap<>();
			body.put("timestamp", Instant.now().toString());
			body.put("status", status.value());
			body.put("error", status.getReasonPhrase());
			body.put("message", message);
			return ResponseEntity.status(status).body(body);
		}
	}
}
