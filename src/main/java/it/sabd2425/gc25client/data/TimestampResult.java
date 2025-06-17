package it.sabd2425.gc25client.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimestampResult {
    @JsonProperty("timestamp")
    private final String timestamp;

    @JsonCreator
    public TimestampResult(@JsonProperty("timestamp") String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TimestampResult{" +
                "timestamp='" + timestamp + '\'' +
                '}';
    }

    public String getTimestamp() {
        return timestamp;
    }
}
