package com.example.sber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/error").permitAll()
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/api/**").authenticated()
						.anyRequest().denyAll()
				)
				.formLogin(form -> form.disable())
				.httpBasic(basic -> basic.disable())
				.addFilterBefore(xUserIdAuthFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(
				"http://localhost:8080",
				"http://127.0.0.1:8080",
				"http://10.0.2.2:8080"
		));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Content-Type", "X-User-Id", "Authorization"));
		configuration.setExposedHeaders(List.of("Content-Type"));
		configuration.setAllowCredentials(false);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public OncePerRequestFilter xUserIdAuthFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected boolean shouldNotFilter(HttpServletRequest request) {
				String path = request.getServletPath();
				return !path.startsWith("/api/");
			}

			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				String userId = request.getHeader("X-User-Id");
				if (userId == null || userId.isBlank()) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing X-User-Id header");
					return;
				}
				try {
					long parsedId = Long.parseLong(userId);
					if (parsedId <= 0) {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid X-User-Id header");
						return;
					}
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							parsedId,
							null,
							List.of(new SimpleGrantedAuthority("ROLE_USER"))
					);
					SecurityContextHolder.getContext().setAuthentication(auth);
					filterChain.doFilter(request, response);
				} catch (NumberFormatException ex) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid X-User-Id header");
				}
			}
		};
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