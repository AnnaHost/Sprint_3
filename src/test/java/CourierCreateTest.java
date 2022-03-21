
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Create courier")
    public void canCreateCourier() {
        Courier courier = Courier.getRandom();
        Response response = sendPostRequestCourierRegistration(courier);
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("ok", equalTo(true));
        courier.deleteCourier(courier);
    }

    @Test
    @DisplayName("Cannot create 2 equal couriers")
    public void cantCreateTwoEqualCouriers() {
        Courier courier = Courier.getRandom();
        sendPostRequestCourierRegistration(courier);
        Response response = sendPostRequestCourierRegistration(courier);
        response.then().assertThat().statusCode(409);
        response.then().assertThat().body("message",equalTo("Этот логин уже используется. Попробуйте другой."));
        courier.deleteCourier(courier);
    }

    @Test
    @DisplayName("Cannot create courier without login")
    public void cantCreateCourierWithoutLogin() {
        Courier courier = Courier.getRandom();
        courier.setLogin(null);
        Response response = sendPostRequestCourierRegistration(courier);
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Cannot create courier without password")
    public void cantCreateCourierWithoutPassword() {
        Courier courier = Courier.getRandom();
        courier.setPassword(null);
        Response response = sendPostRequestCourierRegistration(courier);
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Send POST request")
    public Response sendPostRequestCourierRegistration(Courier courier) {
        Response response = given().header("Content-type", "application/json").and().body(courier).post("/api/v1/courier");
        return response;
    }

}