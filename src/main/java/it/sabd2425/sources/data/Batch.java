package it.sabd2425.sources.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

public class Batch implements Serializable {
    private final int batchId;
    private final int tileId;
    private final String printId;
    private final int layer;
    private final int[][] tif;

    @JsonCreator
    public Batch(
            @JsonProperty("batch_id") int batchId,
            @JsonProperty("tile_id") int tileId,
            @JsonProperty("print_id") String printId,
            @JsonProperty("layer") int layer,
            @JsonProperty("tif") @JsonDeserialize(using = TifImageConverter.class)
            int[][] tif) {
        this.batchId = batchId;
        this.tileId = tileId;
        this.printId = printId;
        this.layer = layer;
        this.tif = tif;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchId=" + batchId +
                ", tileId=" + tileId +
                ", printId=" + printId +
                ", layer=" + layer +
                ", tif=(" + tif.length + ", " + tif[0].length + ")" +
                '}';
    }

    public int getBatchId() {
        return batchId;
    }

    public int getTileId() {
        return tileId;
    }

    public String getPrintId() {
        return printId;
    }

    public int getLayer() {
        return layer;
    }

    public int[][] getTif() {
        return tif;
    }
}
