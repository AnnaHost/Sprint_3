package com.ya;

import com.github.javafaker.Faker;

public class CourierGenerator {

    public static Courier getRandom(){
        Faker faker = new Faker();
        return new Courier(faker.internet().password(),faker.internet().password(),faker.name().firstName());
    }
}
