package ru.glebov.wallet_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMapper extends ObjectMapper {

    private static final ObjectMapper MAPPER = new JacksonObjectMapper();

    private JacksonObjectMapper() {
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}