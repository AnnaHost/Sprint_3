package com.ya;

import io.restassured.response.ValidatableResponse;

public class CourierCredentials {
    private final String login;
    private final String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
