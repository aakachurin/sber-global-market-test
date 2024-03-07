package com.aakachurin;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.function.Supplier;

import static com.aakachurin.openapi.invoker.JacksonObjectMapper.jackson;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;

public class DefaultWeatherRequestSpec implements Supplier<RequestSpecification> {

    @Override
    public RequestSpecification get() {
        return RestAssured.given()
                .config(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(jackson())))
                .baseUri(System.getProperty("weatherApiUri"))
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssured())
                .log().all();
    }
}
