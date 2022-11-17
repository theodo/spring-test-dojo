package com.gmi.testdsl.fakeapplication.clients.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityApiRequestDto {

    @NotNull
    String productSlug;

    @NotNull
    Long projectId;

    @NotNull
    String step;
}
