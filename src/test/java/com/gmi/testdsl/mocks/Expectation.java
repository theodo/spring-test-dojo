package com.gmi.testdsl.mocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockserver.client.ForwardChainExpectation;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.mockserver.matchers.Times;
import org.mockserver.model.JsonBody;

import java.nio.charset.StandardCharsets;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpStatusCode.OK_200;
import static org.mockserver.model.MediaType.APPLICATION_JSON_UTF_8;

public class Expectation {

    private final ForwardChainExpectation chainExpectation;

    private Expectation(ClientAndServer clientAndServer, String path, String body, String verb) {
        chainExpectation = clientAndServer
                .when(
                        request()
                                .withMethod(verb)
                                .withPath(path)
                                .withContentType(APPLICATION_JSON_UTF_8)
                                .withBody(new JsonBody(body, StandardCharsets.UTF_8, MatchType.STRICT)),
                        Times.once()
                );
    }

    public static Expectation onPOST(ClientAndServer clientAndServer, String path, String body) {
        return new Expectation(clientAndServer, path, body, "POST");
    }

    public static <T> Expectation onPOST(ClientAndServer clientAndServer, String path, T body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return new Expectation(clientAndServer, path, objectMapper.writeValueAsString(body), "POST");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void succeed(String body) {
        chainExpectation.respond(
                response()
                        .withStatusCode(OK_200.code())
                        .withContentType(APPLICATION_JSON_UTF_8)
                        .withBody(body)
        );
    }
    public <T> void succeed(T body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            chainExpectation.respond(
                    response()
                            .withStatusCode(OK_200.code())
                            .withContentType(APPLICATION_JSON_UTF_8)
                            .withBody( objectMapper.writeValueAsString(body))
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
