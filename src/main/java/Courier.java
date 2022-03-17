import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.zip.CheckedOutputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Courier {

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {
    }

    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void deleteCourier(Courier courier) {
        int id = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post("/api/v1/courier/login")
                .then().extract().body().path("id");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .delete("/api/v1/courier/{id}",id);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("ok", equalTo(true));

    }

    private String login;
    private String password;
    private String firstName;

}
