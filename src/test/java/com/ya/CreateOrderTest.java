package com.ya;


import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String color;
    OrderClient orderClient;
    Order order;

    public CreateOrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters // добавили аннотацию
    public static Object[][] getColor() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK, GREY"},
                {null}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = new Order(color);
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    public void createOrderWithColors() {
        ValidatableResponse validatableResponse = orderClient.create(order);
        validatableResponse.assertThat().statusCode(SC_CREATED);
        validatableResponse.assertThat().body("track", is(not(0)));
    }


}

