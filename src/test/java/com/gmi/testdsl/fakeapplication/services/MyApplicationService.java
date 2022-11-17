package com.gmi.testdsl.fakeapplication.services;

import com.gmi.testdsl.fakeapplication.clients.EntityServerRetrofitClient;
import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiRequestDto;
import com.gmi.testdsl.fakeapplication.clients.dtos.EntityApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MyApplicationService {
    private final EntityServerRetrofitClient entityClient;

    public EntityApiResponseDto doSomething(String slug, Long projectId, String step){
        Call<EntityApiResponseDto> search = entityClient.search(
                EntityApiRequestDto.builder()
                        .productSlug(slug)
                        .projectId(projectId)
                        .step(step)
                        .build());

        try {
            Response<EntityApiResponseDto> execute = search.execute();
            if(execute.isSuccessful()){
                return execute.body();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
