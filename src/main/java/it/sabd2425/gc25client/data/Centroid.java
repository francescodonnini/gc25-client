package it.sabd2425.gc25client.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Centroid {
    private final int x;
    private final int y;
    private final int count;

    @JsonCreator
    public Centroid(
        @JsonProperty("x") int x,
        @JsonProperty("y") int y,
        @JsonProperty("count") int count) {
        this.x = x;
        this.y = y;
        this.count = count;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCount() {
        return count;
    }
}
