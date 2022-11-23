package com.gmi.testdsl.fakeapplication.clients.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityApiResponseDto {

    @NotNull
    String productSlug;

    @NotNull
    Long projectId;

    @NotNull
    String output;
}
