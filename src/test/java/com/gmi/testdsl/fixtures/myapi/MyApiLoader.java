package com.gmi.testdsl.fixtures.myapi;

import com.gmi.testdsl.fakeapplication.controllers.dtos.MyApiRequestDto;

import static com.gmi.testdsl.fixtures.FixtureLoader.fix;

public class MyApiLoader {
    public static MyApiRequestDto myApiRequest(String fixtureName) { return fix("myapis/"+ fixtureName, MyApiRequestDto.class);}
}
