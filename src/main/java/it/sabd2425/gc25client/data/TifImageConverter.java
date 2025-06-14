package it.sabd2425.gc25client.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TifImageConverter extends JsonDeserializer<int[][]> {
    @Override
    public int[][] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return fromBytes(jsonParser.getBinaryValue());
    }

    public static int[][] fromBytes(byte[] tif) throws IOException {
        var image = ImageIO.read(new ByteArrayInputStream(tif));
        var matrix = new int[image.getWidth()][image.getHeight()];
        var data = image.getData();
        for (var y = 0; y < image.getHeight(); y++) {
            for (var x = 0; x < image.getWidth(); x++) {
                matrix[x][y] = data.getSample(x, y, 0);
            }
        }
        return matrix;
    }
}
