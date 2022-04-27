package ysaak.imeji.exception;

public class ImportException extends Exception {
    public enum ImportError {
        READ_IMAGE,
        WRITE_IMAGE,
        WRITE_THUMBNAIL,
        TECHNICAL
    }

    private final ImportError error;

    public ImportException(ImportError error, String message) {
        super(message);
        this.error = error;
    }

    public ImportException(ImportError error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public ImportError getError() {
        return error;
    }
}
