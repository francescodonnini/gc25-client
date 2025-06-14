package it.sabd2425.sources.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QueryResult {
    private final int batchId;
    private final int query;
    private final String printId;
    private final int tileId;
    private final int saturated;
    private final List<Centroid> centroids;

    @JsonCreator
    public QueryResult(
            @JsonProperty("batch_id") int batchId,
            @JsonProperty("query") int query,
            @JsonProperty("print_id") String printId,
            @JsonProperty("tile_id") int tileId,
            @JsonProperty("saturated") int saturated,
            @JsonProperty("centroids") List<Centroid> centroids) {
        this.batchId = batchId;
        this.query = query;
        this.printId = printId;
        this.tileId = tileId;
        this.saturated = saturated;
        this.centroids = centroids;
    }

    public int getBatchId() {
        return batchId;
    }

    public int getQuery() {
        return query;
    }

    public String getPrintId() {
        return printId;
    }

    public int getTileId() {
        return tileId;
    }

    public int getSaturated() {
        return saturated;
    }

    public List<Centroid> getCentroids() {
        return centroids;
    }
}
