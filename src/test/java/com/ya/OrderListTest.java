package com.ya;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();

    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList() {
        ValidatableResponse validatableResponse = orderClient.get();
        validatableResponse.assertThat().statusCode(SC_OK);
        validatableResponse.assertThat().body("orders._id", notNullValue());
    }
}
