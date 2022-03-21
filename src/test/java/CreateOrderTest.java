import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notANumber;


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
    @DisplayName("Create order with colors")
    public void createOrderWithColors() {
        Order order = new Order(color);
        Response response = sendPostRequestOrderCreate(order);

        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("track", not(notANumber()));
    }

    @Step("Send POST request")
    public Response sendPostRequestOrderCreate(Order order) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Allure.addAttachment("JSON",gson.toJson(order));
        Response response = given().header("Content-type", "application/json").and().body(order).post("/api/v1/orders");
        return response;
    }
}

