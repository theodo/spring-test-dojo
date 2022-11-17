package com.gmi.testdsl.fakeapplication.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfiguration {
    public static final int ENTITY_SERVER_PORT = 9299;

    @Bean
    public EntityServerRetrofitClient getEntityRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:"+ENTITY_SERVER_PORT+"/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(EntityServerRetrofitClient.class);
    }
}
