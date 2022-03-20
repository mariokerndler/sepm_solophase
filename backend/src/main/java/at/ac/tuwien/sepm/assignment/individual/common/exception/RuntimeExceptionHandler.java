package at.ac.tuwien.sepm.assignment.individual.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

/**
 * An ExceptionHandler for handling {@link java.lang.RuntimeException}.
 * Handles exception handling for all layers.
 */
@ControllerAdvice
public class RuntimeExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RuntimeExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);

        var errorMap = new HashMap<>();
        for(var error : ex.getFieldErrors()) {
            var field = error.getField();
            var errorMsg = (errorMap.containsKey(field) ? (errorMap.get(field) + " ") : "");
            errorMsg += error.getDefaultMessage();
            errorMap.put(field, errorMsg);
        }

        return ResponseEntity.unprocessableEntity().body(errorMap);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.debug("Returning HTTP 400 Bad Request", ex);
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @ExceptionHandler(HorseRelationshipException.class)
    protected ResponseEntity<Object> handleHorseRelationshipException(HorseRelationshipException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getErrors());
    }
}
