import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Get order list")
    public void getOrderList() {
        Response response = given()
                .headers("Content-type", "application/json")
                .and()
                .get("/api/v1/orders");
        response.then().extract().body().path("orders");
        response.then().assertThat().body("orders._id", notNullValue());
    }
}
