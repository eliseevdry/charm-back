package ru.eliseev.charm.back.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;

public class JsonMapper {
    private final ObjectMapper objectMapper = com.fasterxml.jackson.databind.json.JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .configure(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .build();

    private static final JsonMapper INSTANCE = new JsonMapper();

    public static JsonMapper getInstance() {
        return INSTANCE;
    }

    public <T> T readValue(InputStream src, Class<T> valueType) throws IOException {
        return objectMapper.readValue(src, valueType);
    }

    public <T> T readValue(Reader src, Class<T> valueType) throws IOException {
        return objectMapper.readValue(src, valueType);
    }

    public void writeValue(Writer w, Object value) throws IOException {
        objectMapper.writeValue(w, value);
    }

}
