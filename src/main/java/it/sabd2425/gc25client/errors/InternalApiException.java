package it.sabd2425.gc25client.errors;

public class InternalApiException extends DefaultApiException {
    private final String endpoint;
    public InternalApiException(String endpoint,  Throwable cause) {
        super(cause);
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
