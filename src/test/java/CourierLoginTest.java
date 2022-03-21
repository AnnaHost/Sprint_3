import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.reset;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }



    @Test
    @DisplayName("Courier can login")
    public void courierCanLogin() {
        ScooterRegisterCourier regCourier = new ScooterRegisterCourier();
        ArrayList<String> list = regCourier.registerNewCourierAndReturnLoginPassword();
        Courier courier = new Courier(list.get(0), list.get(1));
        Response response = sendPostRequestCourierLogin(courier);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("id", not(notANumber()));

    }


    @Test
    @DisplayName("Courier cannot login without login")
    public void courierCannotLoginWithoutLogin() {
        ScooterRegisterCourier regCourier = new ScooterRegisterCourier();
        ArrayList<String> list = regCourier.registerNewCourierAndReturnLoginPassword();
        Courier courier = new Courier(list.get(0));
        Response response = sendPostRequestCourierLogin(courier);

        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Courier cannot login without password")
    public void courierCannotLoginWithoutPassword() {
        ScooterRegisterCourier regCourier = new ScooterRegisterCourier();
        ArrayList<String> list = regCourier.registerNewCourierAndReturnLoginPassword();
        Courier courier = new Courier(list.get(1));
        Response response = sendPostRequestCourierLogin(courier);

        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Courier cannot login without login and password")
    public void courierCannotLoginWithoutLoginAndPassword() {
        Courier courier = new Courier();
        Response response = sendPostRequestCourierLogin(courier);
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Courier cannot login without registration")
    public void courierCannotLoginWithoutRegistration() {
        Courier courier = Courier.getRandom();
        Response response = sendPostRequestCourierLogin(courier);
        response.then().assertThat().statusCode(404);
        response.then().body("message", equalTo("Учетная запись не найдена"));
    }


    @Test
    @DisplayName("Courier cannot login with invalid password")
    public void courierCannotLoginWithInvalidPassword() {
        ScooterRegisterCourier regCourier = new ScooterRegisterCourier();
        ArrayList<String> list = regCourier.registerNewCourierAndReturnLoginPassword();
        Courier courier = new Courier(list.get(0), list.get(1));
        sendPostRequestCourierLogin(courier);
        Courier courierWithInvalidPassword = new Courier(list.get(0), (list.get(1) + "test"));

        Response response = sendPostRequestCourierLogin(courierWithInvalidPassword);
        response.then().assertThat().statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

    @Step("Send POST request")
    public Response sendPostRequestCourierLogin(Courier courier) {
        Response response = given().header("Content-type", "application/json").and().body(courier).post("/api/v1/courier/login");
        return response;
    }

}


