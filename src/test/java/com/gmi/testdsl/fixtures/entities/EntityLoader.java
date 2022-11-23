package com.gmi.testdsl.fixtures.entities;

import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiRequestDto;
import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiResponseDto;

import java.util.HashMap;

import static com.gmi.testdsl.fixtures.FixtureLoader.fix;
import static com.gmi.testdsl.fixtures.FixtureLoader.multiples;

public class EntityLoader {
    public static EntityApiRequestDto entityRequest(String fixtureName) {
        return fix("entities/" + fixtureName, EntityApiRequestDto.class);
    }

    public static EntityApiResponseDto entityResponse(String fixtureName) {
        return fix("entities/" + fixtureName, EntityApiResponseDto.class);
    }

    public static class EntityRequests extends HashMap<String, EntityApiRequestDto> {}
    public static EntityRequests entityRequests(String fixtureName) {
        return multiples("entities/" + fixtureName, EntityRequests.class);
    }
}
