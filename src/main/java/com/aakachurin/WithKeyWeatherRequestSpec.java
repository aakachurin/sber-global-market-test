package com.aakachurin;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.function.Supplier;

public class WithKeyWeatherRequestSpec implements Supplier<RequestSpecification> {

    private final Supplier<RequestSpecification> spec;
    private final String key;

    public WithKeyWeatherRequestSpec(String key, Supplier<RequestSpecification> spec) {
        this.spec = spec;
        this.key = key;
    }

    @Override
    public RequestSpecification get() {
        return RestAssured.given()
                .spec(spec.get())
                .queryParam("key", key );
    }
}
