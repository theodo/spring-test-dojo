package com.gmi.testdsl.fixtures;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FixtureLoader {
    public static <T> T fix(String fixtureName, Class<T> dtoClass) {
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());

        try(InputStream resource = FixtureLoader.class.getClassLoader().getResourceAsStream("fixtures/" + fixtureName)) {
            return mapper.readValue(resource, dtoClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <U, T extends Map<String, U>> T multiples(String fixtureName, Class<T> dtoClass) {
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());

        try(InputStream resource = FixtureLoader.class.getClassLoader().getResourceAsStream("fixtures/" + fixtureName)) {
            return mapper.readValue(resource, dtoClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
