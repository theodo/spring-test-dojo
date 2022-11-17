package com.gmi.testdsl.fakeapplication.clients;

import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiRequestDto;
import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EntityServerRetrofitClient {
    @POST("/v1/entity")
    Call<EntityApiResponseDto> search(@Body EntityApiRequestDto body);
}
