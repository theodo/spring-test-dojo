package com.gmi.testdsl.fakeapplication.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyApiRequestDto {

    @NotNull
    String slug;

    @NotNull
    Long productId;

    @NotNull
    String funnelStep;
}
