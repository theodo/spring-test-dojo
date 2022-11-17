package com.gmi.testdsl;

import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiResponseDto;
import com.gmi.testdsl.fakeapplication.services.MyApplicationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.mockserver.matchers.Times;
import org.mockserver.model.JsonBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

import static com.gmi.testdsl.servers.MockServers.getEntityServer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.MediaType.APPLICATION_JSON_UTF_8;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyApplicationServiceTest {
    private static ClientAndServer aMockServer;
    @Autowired
    private MyApplicationService myApplicationService;

    @BeforeAll
    public static void startup() {
        aMockServer = getEntityServer();
    }

    @BeforeEach
    public void reset() {
        aMockServer.reset();
    }

    @Test
    void testTechnicalWay() {
        String requestDto = "{" +
                "\"productSlug\":\"a slug\"," +
                "\"step\":\"first step\"," +
                "\"projectId\":12345}";

        String responseDto =
                "{\"productSlug\":\"a slug\",\"output\":\"an output\",\"projectId\":12345}";

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
                                .withBody(responseDto)
                );


        EntityApiResponseDto myServerApiResponseDto = myApplicationService.doSomething("a slug", 12345L, "first step");
        assertNotNull(myServerApiResponseDto);
        assertEquals("an output", myServerApiResponseDto.getOutput());


    }
}
