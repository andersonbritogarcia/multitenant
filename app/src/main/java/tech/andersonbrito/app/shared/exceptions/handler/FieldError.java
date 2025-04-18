package tech.andersonbrito.app.shared.exceptions.handler;

public record FieldError(String name, String message) {

    public FieldError(org.springframework.validation.FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
