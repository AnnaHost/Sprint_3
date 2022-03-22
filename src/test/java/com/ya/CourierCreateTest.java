package com.ya;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {

    CourierClient courierClient;
    Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();

    }

    @Test
    @DisplayName("Создание курьера")
    public void canCreateCourier() {
        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse.assertThat().statusCode(SC_CREATED);
        createResponse.assertThat().body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Невозможно создать 2 одинаковых курьеров")
    public void cantCreateTwoEqualCouriers() {
        courierClient.create(courier);
        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse.assertThat().statusCode(SC_CONFLICT);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Невозможно создать курьера без логина")
    public void cantCreateCourierWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse.assertThat().statusCode(SC_BAD_REQUEST);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможно создать курьера без пароля")
    public void cantCreateCourierWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse.assertThat().statusCode(SC_BAD_REQUEST);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}