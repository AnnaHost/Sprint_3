package com.ya;

import com.github.javafaker.Faker;

public class CourierGenerator {

    public static Courier getRandom(){
        Faker faker = new Faker();
        return new Courier(faker.name().firstName(),faker.internet().password(),faker.name().firstName());
    }
}
