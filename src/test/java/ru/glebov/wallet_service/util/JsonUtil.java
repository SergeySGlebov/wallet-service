package ru.glebov.wallet_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

import static ru.glebov.wallet_service.util.JacksonObjectMapper.getMapper;

public class JsonUtil {

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return getMapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }
}
