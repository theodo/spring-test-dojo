package com.gmi.testdsl.fakeapplication.clients.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder()
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityApiRequestDto {

    @NotNull
    String productSlug;

    @NotNull
    Long projectId;

    @NotNull
    @JsonProperty("strVal")
    String step;
}
