package com.gmi.testdsl;

import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiRequestDto;
import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiResponseDto;
import com.gmi.testdsl.fakeapplication.services.MyApplicationService;
import org.intellij.lang.annotations.Language;
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

import static com.gmi.testdsl.fixtures.FixtureLoader.fix;
import static com.gmi.testdsl.fixtures.entities.EntityLoader.entityRequest;
import static com.gmi.testdsl.fixtures.entities.EntityLoader.entityResponse;
import static com.gmi.testdsl.mocks.Expectation.onPOST;
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
                "\"strVal\":\"first step\"," +
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

    @Test
    void testHideSomeDetails() {
        @Language("JSON")
        String requestDto = "{\"productSlug\":\"a slug\",\"strVal\":\"first step\",\"projectId\":12345}";

        @Language("JSON")
        String responseDto = "{\"productSlug\":\"a slug\",\"output\":\"an output\",\"projectId\":12345}";

        onPOST(aMockServer, "/v1/entity", requestDto).succeed(responseDto);

        EntityApiResponseDto myServerApiResponseDto = myApplicationService.doSomething("a slug", 12345L, "first step");
        assertNotNull(myServerApiResponseDto);
        assertEquals("an output", myServerApiResponseDto.getOutput());

    }

    @Test
    void testManageDTO() {
        onPOST(aMockServer, "/v1/entity",
                EntityApiRequestDto.builder()
                        .productSlug("a slug")
                        .projectId(12345L)
                        .step("a step")
                        .build())
                .succeed(EntityApiResponseDto.builder()
                        .projectId(12345L)
                        .productSlug("a slug")
                        .output("an output")
                        .build());

        EntityApiResponseDto myServerApiResponseDto = myApplicationService.doSomething("a slug", 12345L, "a step");
        assertNotNull(myServerApiResponseDto);
        assertEquals("an output", myServerApiResponseDto.getOutput());
    }

    @Test
    void testFixtures() {
        onPOST(aMockServer, "/v1/entity", fix("entities/oneRequest.json", EntityApiRequestDto.class))
                .succeed(fix("entities/oneResponse.json", EntityApiResponseDto.class));

        EntityApiResponseDto myServerApiResponseDto = myApplicationService.doSomething("a slug", 12345L, "a step");
        assertNotNull(myServerApiResponseDto);
        assertEquals("an output", myServerApiResponseDto.getOutput());
    }

    @Test
    void testFixturesDSL() {
        onPOST(aMockServer, "/v1/entity", entityRequest("oneRequest.json"))
                .succeed(entityResponse("oneResponse.json"));

        EntityApiResponseDto myServerApiResponseDto = myApplicationService.doSomething("a slug", 12345L, "a step");
        assertNotNull(myServerApiResponseDto);
        assertEquals("an output", myServerApiResponseDto.getOutput());
    }
}
