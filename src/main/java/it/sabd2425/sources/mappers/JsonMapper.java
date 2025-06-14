package it.sabd2425.sources.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonMapper() {
    }

    public static <T> T fromString(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public static <T> String toString(T object) throws IOException {
        return mapper.writeValueAsString(object);
    }
}
