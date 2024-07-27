package site.nomoreparties.stellarburgers.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class UserAPI {
    @Step("Регистрация пользователя через API")
    public Response sendPostAPIAuthRegister(User user) {
        baseURI = Url.URL;
        return given().header("Content-type", "application/json; charset=utf-8").log().body().body(user)
                .post("/api/auth/register");
    }

    @Step("Вход пользователя через API")
    public Response sendPostRequestAPIAuthLogin(User user) {
        baseURI = Url.URL;
        return given().header("Content-type", "application/json; charset=utf-8").log().body().body(user)
                .post("/api/auth/login");
    }

    @Step("Сохранить токен")
    public String getTokenFromResponse(Response response) {
        return response.then().extract().body().path("accessToken").toString().substring(7);
    }

    @Step("Удаление пользователя через API")
    public void sendDeleteAPIAuthUserByToken(String token) {
        baseURI = Url.URL;
        given().header("Authorization", format("Bearer %s", token)).log().body()
                .delete("/api/auth/user");
    }

}
