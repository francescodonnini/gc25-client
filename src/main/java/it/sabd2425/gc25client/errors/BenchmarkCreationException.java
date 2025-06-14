package it.sabd2425.gc25client.errors;

import it.sabd2425.gc25client.data.BenchConfig;

public class BenchmarkCreationException extends HttpRequestException {

    public BenchmarkCreationException(int statusCode, BenchConfig config) {
        super("create", statusCode, "cannot create benchmark from configuration: " + config);
    }
}
