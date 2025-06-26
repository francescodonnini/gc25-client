package it.sabd2425.gc25client.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonMapper {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T fromString(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public <T> String toString(T object) throws IOException {
        return mapper.writeValueAsString(object);
    }
}
