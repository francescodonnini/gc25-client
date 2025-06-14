package it.sabd2425.sources.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

public class MessagePackMapper {
    private static final ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());

    private MessagePackMapper() {
    }

    public static <T> T fromBytes(byte[] bytes, Class<T> clazz) throws IOException {
        return mapper.readValue(bytes, clazz);
    }

    public static <T> byte[] toBytes(T object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }
}
