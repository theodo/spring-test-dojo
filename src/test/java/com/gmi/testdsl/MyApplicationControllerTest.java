
package com.gmi.testdsl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiResponseDto;
import com.gmi.testdsl.fakeapplication.controllers.dtos.MyApiRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.mockserver.matchers.Times;
import org.mockserver.model.JsonBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static com.gmi.testdsl.servers.MockServers.getEntityServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.MediaType.APPLICATION_JSON_UTF_8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class MyApplicationControllerTest {
    private static ClientAndServer aMockServer;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void startup() {
        aMockServer = getEntityServer();
    }

    @BeforeEach
    public void reset() {
        aMockServer.reset();
    }

    @Test
    void testTechnicalWay() throws Exception {
        String requestDto = "{" +
                "\"productSlug\":\"a slug\"," +
                "\"step\":\"first step\"," +
                "\"projectId\":12345}";

        aMockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/v1/entity")
                                .withContentType(APPLICATION_JSON_UTF_8)
                                .withBody(new JsonBody(requestDto, StandardCharsets.UTF_8, MatchType.STRICT)),
                        Times.once()
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(APPLICATION_JSON_UTF_8)
                                .withBody(objectMapper.writeValueAsString(EntityApiResponseDto.builder()
                                                .output("an output")
                                                .productSlug("a slug")
                                                .projectId(12345L)
                                        .build()))
                );

         mockMvc
                .perform(
                        post("/myApi")
                                .content(objectMapper.writeValueAsString(
                                        MyApiRequestDto.builder()
                                                .funnelStep("first step")
                                                .productId(12345L)
                                                .slug("a slug")
                                                .build()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("a slug"))
                .andExpect(jsonPath("$.productId").value(12345L))
                .andExpect(jsonPath("$.status").value("an output"));


    }
}
