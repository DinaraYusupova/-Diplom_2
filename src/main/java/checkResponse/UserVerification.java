package checkResponse;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


//Методы для проверки ответов при создании заказа
public class UserVerification {
    @Step("Compare status code")
    public void compareStatusCode(ValidatableResponse response, int code){
        response.statusCode(code);
    }
    @Step("Compare success status")
    public void compareStatus(ValidatableResponse response,boolean successStatus){
        response.assertThat().body("success", equalTo(successStatus));
    }
    @Step("Compare user email in response")
    public void compareUserEmail(ValidatableResponse response, String email){
        response.assertThat().body("user.email", equalTo(email));
    }
    @Step("Compare user name in response")
    public void compareUserName(ValidatableResponse response, String name){
        response.assertThat().body("user.name", equalTo(name));
    }
    @Step("Compare response message")
    public void compareResponseMessage(ValidatableResponse response, String message){
        response.assertThat().body("message", equalTo(message));
    }
    @Step("Check accessToken not null")
    public void checkAccessToken(ValidatableResponse response){
        response.assertThat().body("accessToken", notNullValue());
    }
    @Step("Check refreshToken not null")
    public void checkRefreshToken(ValidatableResponse response){
        response.assertThat().body("refreshToken", notNullValue());
    }
}
