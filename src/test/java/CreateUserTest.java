import Client.UserClient;
import DataGenerator.UserGenerator;
import Models.User;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CreateUserTest {
    private User user;
    private UserClient userClient;
    private String accessToken;
    private final static String messageForConflict = "User already exists";

    @Before
    public void setUp(){
        userClient = new UserClient();
    }


    @Test
    @DisplayName("Check status code, status and token of creating user")
    public void createNewUser() {
      //  String json = "{\"email\": \"test-data@yandex.ru\", \"password\": \"password\", \"name\": \"Username\"}";
        user = UserGenerator.getNewData();
        ValidatableResponse responseCreate = userClient.create(user);
        compareStatusCode(responseCreate,SC_OK);
        compareStatus(responseCreate,true);
        checkAccessToken(responseCreate);
        checkRefreshToken(responseCreate);
        accessToken = responseCreate.extract().path("accessToken");
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check status code, status and message of creating double user")
    public void createDoubleCourier() throws InterruptedException {
        user = UserGenerator.getNewData();
        ValidatableResponse responseCreate = userClient.create(user); //создаем нового курьера
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        ValidatableResponse doubleResponseCreate = userClient.create(user); //создаем еще одного курьера с теми же данными
        compareStatusCode(doubleResponseCreate,SC_FORBIDDEN);
        compareStatus(doubleResponseCreate,false);
        compareResponseMessage(doubleResponseCreate,messageForConflict);
        accessToken = responseCreate.extract().path("accessToken");
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check status code, status and token of creating user with wrong email")
    public void createUserWithWrongEmail() {
        user = UserGenerator.getDataWithWrongLogin();
        ValidatableResponse responseCreate = userClient.create(user);
        compareStatusCode(responseCreate,SC_INTERNAL_SERVER_ERROR);
    }




    @Step("Compare status code")
    public void compareStatusCode(ValidatableResponse response, int code){
        response.statusCode(code);
    }

    @Step("Compare status")
    public void compareStatus(ValidatableResponse response,boolean successStatus){
        response.assertThat().body("success", equalTo(successStatus));
    }

    @Step("Compare response message")
    public void compareResponseMessage(ValidatableResponse response, String message){
        response.assertThat().body("message", equalTo(message));
    }

    @Step("Compare accessToken not null")
    public void checkAccessToken(ValidatableResponse response){
        response.assertThat().body("accessToken", notNullValue());
    }

    @Step("Compare refreshToken not null")
    public void checkRefreshToken(ValidatableResponse response){
        response.assertThat().body("refreshToken", notNullValue());
    }

}
