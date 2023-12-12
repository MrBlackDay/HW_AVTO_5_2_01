package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Locale;
import lombok.Value;

import static io.restassured.RestAssured.given;


public class DataGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker (new Locale("en"));

    private DataGenerator() {
    }

    private static RegistrationDto sendRequest (RegistrationDto user){
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                //.contentType(ContentType.JSON);
                .statusCode(200); // код 200 OK
        return user;
    }

    public static String getRandomLogin (){
        return faker.name().username();
    }
    public static String getRandomPassword (){
        return faker.internet().password();
    }

    public static class Registration{
        private Registration(){
        }

        public static RegistrationDto getUser (String status){
            return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        }

        public static RegistrationDto getRegisteredUser (String status){
            return sendRequest(getUser(status));
        }

    }
    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}