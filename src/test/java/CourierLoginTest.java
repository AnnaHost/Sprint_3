import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Courier can login")
    public void courierCanLogin() {
        ScooterRegisterCourier courier = new ScooterRegisterCourier();
        ArrayList<String> list = courier.registerNewCourierAndReturnLoginPassword();
        Allure.addAttachment("login", list.get(0));
        Allure.addAttachment("password", list.get(1));
        String loginRequestBody = "{\"login\":\"" + list.get(0) + "\","
                + "\"password\":\"" + list.get(1) + "\"}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Courier cannot login without login")
    public void courierCannotLoginWithoutLogin() {
        ScooterRegisterCourier courier = new ScooterRegisterCourier();
        ArrayList<String> list = courier.registerNewCourierAndReturnLoginPassword();
        Allure.addAttachment("login", list.get(0));
        String loginRequestBody = "{\"login\":\"" + list.get(0) + "\"}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(200);
        response.then().body("message",equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Courier cannot login without password")
    public void courierCannotLoginWithoutPassword() {
        ScooterRegisterCourier courier = new ScooterRegisterCourier();
        ArrayList<String> list = courier.registerNewCourierAndReturnLoginPassword();
        Allure.addAttachment("password", list.get(1));
        String loginRequestBody = "{\"password\":\"" + list.get(1) + "\"}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(200);
        response.then().body("message",equalTo("Недостаточно данных для входа"));
    }
}
