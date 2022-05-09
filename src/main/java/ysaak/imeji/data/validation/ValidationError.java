package ysaak.imeji.data.validation;

public class ValidationError {
    private final String field;
    private final String errorMessage;

    public ValidationError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
