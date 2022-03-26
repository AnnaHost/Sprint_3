package com.ya;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH = "api/v1/courier";

    @Step("Логин курьером")
    public ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH+"/login")
                .then();
    }
    @Step("Регистрация курьера")
    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int CourierId){
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH+"{id}",CourierId)
                .then();
    }
}
