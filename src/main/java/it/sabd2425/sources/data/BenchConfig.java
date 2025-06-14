package it.sabd2425.sources.data;

import java.io.Serializable;

public class BenchConfig implements Serializable {
    private final String apiToken;
    private final String name;
    private final int maxBatches;
    private final boolean test;

    public BenchConfig(String apiToken, String name, int maxBatches, boolean test) {
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

    public int getMaxBatches() {
        return maxBatches;
    }

    public boolean isTest() {
        return test;
    }
}
