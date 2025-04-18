package tech.andersonbrito.app.shared.exceptions.handler;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tech.andersonbrito.app.shared.exceptions.BusinessException;
import tech.andersonbrito.app.shared.exceptions.EntityAlreadyExistsException;
import tech.andersonbrito.app.shared.exceptions.InternalServerException;
import tech.andersonbrito.app.shared.exceptions.UnprocessableEntityException;

@ControllerAdvice
public class AppExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        var fieldErrors = ex.getBindingResult().getFieldErrors();
        var problemFields = fieldErrors.stream().map(FieldError::new).toList();
        var problem = new Problem(HttpStatus.BAD_REQUEST, "Resource not found", "One or more fields are invalid", problemFields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        var problem = new Problem(HttpStatus.BAD_REQUEST, "Business error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        var problem = new Problem(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problem);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        var problem = new Problem(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        var problem = new Problem(HttpStatus.NOT_FOUND, "Not found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<?> handleUnprocessableEntityException(UnprocessableEntityException ex, WebRequest request) {
        var problem = new Problem(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable entity", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        var problem = new Problem(HttpStatus.CONFLICT, "Conflict", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> handleInternalServerException(InternalServerException ex, WebRequest request) {
        LOGGER.error("Internal server error", ex);
        var problem = new Problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
        LOGGER.error("Internal server error", ex);
        var problem = new Problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, WebRequest request) {
        LOGGER.error("Internal server error", ex);
        var problem = new Problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }
}
