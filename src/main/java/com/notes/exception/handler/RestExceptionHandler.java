package com.notes.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.notes.exception.ErrorDetail;
import com.notes.exception.NotFoundException;
import com.notes.exception.UnauthorizedUserException;
import com.notes.exception.ValidationError;

/**
 * 
 * @author Jovani Brasil
 *
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info("handleMethodArgumentNotValid");
		List<ValidationError> errors = ex.getBindingResult().getFieldErrors()
				.stream().map(e -> new ValidationError(e.getDefaultMessage(), e.getField(), e.getRejectedValue()))
				.collect(Collectors.toList());
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message("Invalid field values")
				.code(status.value())
				.status(status.getReasonPhrase())
				.objectName(ex.getBindingResult().getObjectName())
				.errors(errors).build();
		return ResponseEntity.badRequest().body(errorDetail);
	}

	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<?> unauthorizedUserException(UnauthorizedUserException rnfException){
		log.info("UnauthorizedUserException");
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(rnfException.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetail);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleResourceNotFound(NotFoundException rnfException){
		log.info("handleResourceNotFound");
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(rnfException.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
	}

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ResponseEntity<?> exception(Exception ex) {
		log.info("The server cannot process the received request. {}", ex.getStackTrace().toString());
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message("The server cannot process the request.")
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
	}

}
