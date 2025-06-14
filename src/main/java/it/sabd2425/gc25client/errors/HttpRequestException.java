package it.sabd2425.gc25client.errors;

public class HttpRequestException extends DefaultApiException {
    private final String endpoint;
    private final int statusCode;

    public HttpRequestException(String endpoint, int statusCode, String message, Throwable cause) {
        super(cause, message);
        this.endpoint = endpoint;
        this.statusCode = statusCode;
    }

    public HttpRequestException(String endpoint, int statusCode, String message) {
        super(message);
        this.endpoint = endpoint;
        this.statusCode = statusCode;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
