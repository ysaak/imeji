package ysaak.imeji.utils;

import ysaak.imeji.data.validation.ValidationError;
import ysaak.imeji.exception.ValidationException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Validator {
    private Set<ValidationError> errors = new HashSet<>();

    public Set<ValidationError> getErrors() {
        return errors;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public void orElseThrow(String message) throws ValidationException {
        if (errors.size() > 0) {
            throw new ValidationException(getErrors(), message);
        }
    }

    public void notNull(Object obj, String fieldName, String errorMessage) {
        if (obj == null) {
            errors.add(new ValidationError(fieldName, errorMessage));
        }
    }

    public void notBlank(String str, String fieldName, String errorMessage) {
        if (str == null || str.isBlank()) {
            errors.add(new ValidationError(fieldName, errorMessage));
        }
    }

    public void regex(String str, String regex, String fieldName, String errorMessage) {
        if (!Pattern.matches(regex, str)) {
            errors.add(new ValidationError(fieldName, errorMessage));
        }
    }

    public void isFalse(boolean expr, String fieldName, String errorMessage) {
        if (expr) {
            errors.add(new ValidationError(fieldName, errorMessage));
        }
    }
}
