package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.DataGenerator.Registration.getUser;
import static ru.netology.DataGenerator.getRandomLogin;
import static ru.netology.DataGenerator.getRandomPassword;


public class IbankTest {
    @Test
    @DisplayName("Should successfully if login and password are registered")
    void shouldSuccessfully(){
        open("http://localhost:9999");
        var registeredUser = getRegisteredUser ("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2")
                .shouldHave(Condition.exactText("Личный кабинет"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should unsuccessfully if login and password are not registered")
    void shouldUnsuccessfully(){
        open("http://localhost:9999");
        var notRegisteredUser = getUser ("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id= 'error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should unsuccessfully if login is not registered")
    void shouldUnsuccessfullyUnvolidLogin(){
        open("http://localhost:9999");
        var registeredUser = getRegisteredUser ("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id= 'error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should unsuccessfully if password is not registered")
    void shouldUnsuccessfullyUnvolidPassword(){
        open("http://localhost:9999");
        var registeredUser = getRegisteredUser ("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id= 'error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should unsuccessfully if user is blocked")
    void shouldUnsuccessfullyUserIsBlocked(){
        open("http://localhost:9999");
        var blockedUser = getRegisteredUser ("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id= 'error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }
}
