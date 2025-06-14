package it.sabd2425.gc25client.errors;

public class DefaultApiException extends Exception {
    public DefaultApiException(String message) {
        super(message);
    }

    public DefaultApiException(Throwable cause) {
        super(cause);
    }

    public DefaultApiException(Throwable cause, String message) {
        super(message, cause);
    }
}
