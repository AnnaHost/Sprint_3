import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String color;

    public CreateOrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters // добавили аннотацию
    public static Object[][] getColor() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK, GREY"},
                {""}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Create empty order")
    public void createEmptyOrderTest() {
        Order order = new Order();
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .post("/api/v1/orders");

        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("track", not(notANumber()));
    }

    @Test
    @DisplayName("Create order with colors")
    public void createOrderWithColors() {
        Order order = new Order(color);
        Allure.addAttachment("Color",color);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .post("/api/v1/orders");

        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("track", not(notANumber()));
    }
}

