package it.sabd2425.gc25client.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

public class MessagePackMapper {
    private final ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());

    public <T> byte[] toBytes(T object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }
}