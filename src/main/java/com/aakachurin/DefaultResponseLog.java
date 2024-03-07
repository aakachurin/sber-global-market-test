package com.aakachurin;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;

import java.util.function.Supplier;

public class DefaultResponseLog implements Supplier<ResponseSpecBuilder> {
    private final Supplier<ResponseSpecBuilder> spec;

    public DefaultResponseLog(Supplier<ResponseSpecBuilder> spec) {
        this.spec = spec;
    }

    public DefaultResponseLog(ResponseSpecBuilder spec) {
        this(() -> spec);
    }

    @Override
    public ResponseSpecBuilder get() {
        return spec.get().log(LogDetail.ALL);
    }
}
