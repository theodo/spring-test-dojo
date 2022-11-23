package com.gmi.testdsl.mocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class RestCall {
    public static <T> ResultActions doPOST(MockMvc mockMvc, String uri, T body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return mockMvc.perform(
                    post(uri).content(objectMapper.writeValueAsString(body)).contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
