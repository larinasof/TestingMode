package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@Data
public class DataGenerator {
    private static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll (RegistrationDto registrationDto){
        given()
                .spec(requestSpecification)
                .body(registrationDto)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationDto validActiveUser(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        String status = "active";
        RegistrationDto registrationDto = new RegistrationDto(login, password, status);
        setUpAll(registrationDto);
        return registrationDto;
    }

    public static RegistrationDto validBlockedUser(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        String status = "blocked";
        RegistrationDto registrationDto = new RegistrationDto(login, password, status);
        setUpAll(registrationDto);
        return registrationDto;
    }

    public static RegistrationDto invalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String login = "vasya";
        String password = faker.internet().password();
        String status = "active";
        setUpAll(new RegistrationDto(login, password, status));
        return new RegistrationDto("kolya", password, status);
    }

    public static RegistrationDto invalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = "password";
        String status = "active";
        setUpAll(new RegistrationDto(login, password, status));
        return new RegistrationDto(login, "pasword", status);
    }
}