package ch.template.remote.error;

public class APIError {
    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public long getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String toString() {
        return String.format("[timestamp: %s, status: %s, error: %s, exception: %s, message: %s, path: %s]",
                timestamp,
                status,
                error,
                exception,
                message,
                path);
    }
}
