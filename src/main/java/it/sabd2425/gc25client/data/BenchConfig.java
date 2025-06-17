package it.sabd2425.gc25client.data;

import java.io.Serializable;
import java.util.Optional;

public class BenchConfig implements Serializable {
    private final String apiToken;
    private final String name;
    private final Integer maxBatches;
    private final boolean test;

    public BenchConfig(String apiToken, String name, boolean test) {
        this(apiToken, name, null, test);
    }

    public BenchConfig(String apiToken, String name, Integer maxBatches, boolean test) {
        this.apiToken = apiToken;
        this.name = name;
        this.maxBatches = maxBatches;
        this.test = test;
    }

    @Override
    public String toString() {
        return "BenchConfig{" +
                "apiToken='" + apiToken + '\'' +
                ", name='" + name + '\'' +
                ", maxBatches=" + maxBatches +
                ", test=" + test +
                '}';
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getName() {
        return name;
    }

    public Optional<Integer> getMaxBatches() {
        return Optional.ofNullable(maxBatches);
    }

    public boolean isTest() {
        return test;
    }
}
