package com.notes.exceptions.handlers;

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

import com.notes.exceptions.NotFoundException;
import com.notes.exceptions.UnauthorizedUserException;
import com.notes.services.models.ErrorDetail;
import com.notes.services.models.Response;
import com.notes.services.models.ValidationError;

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
		System.out.println("handleMethodArgumentNotValid");
		List<ValidationError> errors = ex.getBindingResult().getFieldErrors()
				.stream().map(e -> new ValidationError(e.getDefaultMessage(), e.getField(), e.getRejectedValue()))
				.collect(Collectors.toList());
		ErrorDetail error = new ErrorDetail.Builder()
				.message("Invalid field values")
				.code(status.value())
				.status(status.getReasonPhrase())
				.objectName(ex.getBindingResult().getObjectName())
				.errors(errors).build();
		Response<Object> response = new Response<Object>();
		response.addError(error);
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<Response<?>> UnauthorizedUserException(UnauthorizedUserException rnfException){
		log.info("UnauthorizedUserException");
		Response<String> response = new Response<String>();
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(rnfException.getMessage())
				.build();
		response.addError(errorDetail);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Response<?>> handleResourceNotFound(NotFoundException rnfException){
		log.info("handleResourceNotFound");
		Response<String> response = new Response<>();
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(rnfException.getMessage())
				.build();
		response.addError(errorDetail);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ResponseEntity<Object> exception(Exception ex) {
		log.info("The server cannot process the received request. {}", ex.getStackTrace());
		Response<String> response = new Response<>();
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message("The server cannot process the request.")
				.build();
		response.addError(errorDetail);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
