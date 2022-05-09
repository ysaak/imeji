package ysaak.imeji.exception;

import ysaak.imeji.data.validation.ValidationError;

import java.util.Set;

public class ValidationException extends Exception {
    private Set<ValidationError> errors;

    public ValidationException(Set<ValidationError> errors, String message) {
        super(message);
        this.errors = errors;
    }

    public Set<ValidationError> getErrors() {
        return errors;
    }
}
