package com.aakachurin;

import com.aakachurin.openapi.rest.api.ApisApi;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import java.util.function.Supplier;

import static com.aakachurin.openapi.invoker.JacksonObjectMapper.jackson;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;

public class DefaultApiClient implements Supplier<ApisApi> {

    private final Supplier<RequestSpecification> spec;

    public DefaultApiClient(Supplier<RequestSpecification> spec) {
        this.spec = spec;
    }

    @Override
    public ApisApi get() {
        RestAssured.defaultParser = Parser.JSON;

        return ApisApi.apis(() -> new RequestSpecBuilder()
                .setConfig(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(jackson())))
                .addRequestSpecification(spec.get())
        );
    }
}
