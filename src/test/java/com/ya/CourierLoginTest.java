package com.ya;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CourierLoginTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("com.ya.Авторизация курьера с корректными данными")
    public void courierCanLoginWithValidCredentials() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        loginResponse.assertThat().statusCode(SC_OK);
        courierId = loginResponse.extract().path("id");
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }


    @Test
    @DisplayName("com.ya.Курьер не может авторизоваться без логина")
    public void courierCannotLoginWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        loginResponse.assertThat().statusCode(SC_BAD_REQUEST);
        loginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("com.ya.Курьер не может авторизоваться без пароля")
    public void courierCannotLoginWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        loginResponse.assertThat().statusCode(SC_BAD_REQUEST);
        loginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }




    @Test
    @DisplayName("com.ya. Курьер не может авторизоваться без регистрации")
    public void courierCannotLoginWithoutRegistration() {
        Faker faker = new Faker();
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(faker.name().firstName(),faker.number().digit()));
        loginResponse.assertThat().statusCode(SC_NOT_FOUND);
        loginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }


    @Test
    @DisplayName("com.ya. Курьер не может авторизоваться с некорректным паролем")
    public void courierCannotLoginWithInvalidPassword() {
        Faker faker = new Faker();
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(),faker.number().digit()));
        loginResponse.assertThat().statusCode(SC_NOT_FOUND);
        loginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

}


