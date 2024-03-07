package com.aakachurin.steps;

import com.aakachurin.DefaultApiClient;
import com.aakachurin.DefaultResponseLog;
import com.aakachurin.DefaultWeatherRequestSpec;
import com.aakachurin.WithKeyWeatherRequestSpec;
import com.aakachurin.openapi.rest.api.ApisApi;
import com.aakachurin.openapi.rest.model.Error400;
import com.aakachurin.openapi.rest.model.RealtimeWeather200Response;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class WeatherStepDefinition {

  private Supplier<ApisApi> apiClient;
  private Supplier<Response> currentWeatherFunction;

  @Дано("пользователю задан ключ API {string}")
  public void userWithKey(String key) {
    switch (key) {
      case "default" ->
          apiClient = new DefaultApiClient(new WithKeyWeatherRequestSpec(System.getProperty("weatherApiKey"), new DefaultWeatherRequestSpec()));
      case "" -> apiClient = new DefaultApiClient(new DefaultWeatherRequestSpec());
      default -> apiClient = new DefaultApiClient(new WithKeyWeatherRequestSpec(key, new DefaultWeatherRequestSpec()));
    }
  }

  @Когда("пользователь запрашивает текущую погоду для города {string}")
  public void getWeather(String city) {
    currentWeatherFunction = () -> {
      ApisApi.RealtimeWeatherOper weatherRequest = apiClient.get()
          .realtimeWeather()
          .respSpec(x -> new DefaultResponseLog(x).get().build());
      if (!city.isEmpty()) {
        weatherRequest.qQuery(city);
      }
      return weatherRequest.execute(Function.identity());
    };
  }

  @Тогда("API должно успешно вернуть следующие значения:")
  public void assertWeather(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    RealtimeWeather200Response currentWeather = currentWeatherFunction.get().then().statusCode(200).extract().as(RealtimeWeather200Response.class);

    Assertions.assertEquals(Double.parseDouble(map.get("Температура")), currentWeather.getCurrent().getTempC(), "Температура не совпадает");
    Assertions.assertEquals(map.get("Погода"), currentWeather.getCurrent().getCondition().getText(), "Погода не совпадает");
  }

  @Тогда("API должно вернуть ошибку:")
  public void assertWeatherError(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    Error400 error = currentWeatherFunction.get().then().statusCode(Integer.parseInt(map.get("HTTP код")))
        .extract().response().getBody().jsonPath().getObject("error", Error400.class);

    Assertions.assertEquals(Integer.parseInt(map.get("Код ошибки")), error.getCode(), "Код ошибки не совпадает");
    Assertions.assertEquals(map.get("Описание"), error.getMessage(), "Описание не совпадает");
  }
}