package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;


public class IbankTest {
    @Test
    @DisplayName("Should successfully")
    void shouldSuccessfully(){
        open("http://localhost:9999");
        var registeredUser = getRegisteredUser ("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2")
                .shouldHave(Condition.exactText("Личный кабинет"), Duration.ofSeconds(5))
                .shouldBe(Condition.visible);
    }


    /*@Test
    @DisplayName("Should successfully")
    void shouldnotSuccessfully(){
        open("http://localhost:9999");
        var registeredUser = getRegisteredUser ("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2")
                .shouldHave(Condition.exactText("Личный кабинет"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }*/
}
