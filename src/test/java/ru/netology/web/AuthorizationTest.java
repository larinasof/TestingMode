package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationDto;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldAuthorizeValidActiveUser(){
        RegistrationDto validActiveUser = DataGenerator.validActiveUser();
        $("[data-test-id='login'] input").setValue(validActiveUser.getUsername());
        $("[data-test-id='password'] input").setValue(validActiveUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".heading").shouldHave(exactText("Личный кабинет"));
    }

    @Test
    void shouldNotAuthorizeValidBlockedUser(){
        RegistrationDto validBlockedUser = DataGenerator.validBlockedUser();
        $("[data-test-id='login'] input").setValue(validBlockedUser.getUsername());
        $("[data-test-id='password'] input").setValue(validBlockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Пользователь заблокирован"));
    }
}
