package com.gmi.testdsl.fakeapplication.controllers;

import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiResponseDto;
import com.gmi.testdsl.fakeapplication.controllers.dtos.MyApiRequestDto;
import com.gmi.testdsl.fakeapplication.controllers.dtos.MyApiResponseDto;
import com.gmi.testdsl.fakeapplication.services.MyApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("myApi")
@AllArgsConstructor
public class MyApplicationController {
    private final MyApplicationService applicationService;

    @PostMapping
    public MyApiResponseDto doSomething(
        @Valid @RequestBody MyApiRequestDto myApiRequestDto
    ) {
        try {
            EntityApiResponseDto responseDto = applicationService.doSomething(
                    myApiRequestDto.getSlug(),
                    myApiRequestDto.getProductId(),
                    myApiRequestDto.getFunnelStep());

            return MyApiResponseDto.builder()
                    .productId(responseDto.getProjectId())
                    .slug(responseDto.getProductSlug())
                    .status(responseDto.getOutput())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during service call", e);
        }
    }
}
