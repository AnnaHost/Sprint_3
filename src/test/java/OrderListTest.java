import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class OrderListTest {
    @Test
    @DisplayName("")
    public void getOrderList(){
        Response response = given()
                .headers("Content-type", "application/json")
                .and()
                .get("/api/v1/courier/{id}/ordersCount");
    }
}
