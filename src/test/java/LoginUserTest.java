import Client.UserClient;
import DataGenerator.CredentialsGenerator;
import DataGenerator.UserGenerator;
import Models.Credentials;
import Models.User;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest {
    private User user;
    private Credentials credentials;
    private UserClient userClient;
    private int statusCode = SC_OK;


    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("login user and check statusCode and id")
    public void loginCourier() throws InterruptedException {
        user = UserGenerator.getDefault();
        credentials = CredentialsGenerator.getDefault();
        userClient.create(user); // создаю пользователя, чтобы он точно был в базе
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        ValidatableResponse responseLogin = userClient.login(credentials);
        compareStatusCode(responseLogin, statusCode);
        compareStatus(responseLogin,true);
        checkAccessToken(responseLogin);
        checkRefreshToken(responseLogin);
        checkUserEmail(responseLogin,user.getEmail());
        checkUserName(responseLogin,user.getName());
    }

    @Test
    @DisplayName("double login user and check statusCode and id") //Тест с попыткой повторного логина, с уже залогининным пользователем
    public void doubleLoginCourier() throws InterruptedException {
        user = UserGenerator.getDefault();
        credentials = CredentialsGenerator.getDefault();
        userClient.create(user); // создаю пользователя, чтобы он точно был в базе
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        ValidatableResponse responseLogin = userClient.login(credentials);
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        ValidatableResponse doubleResponseLogin = userClient.login(credentials);
        compareStatusCode(doubleResponseLogin, statusCode);
        compareStatus(doubleResponseLogin,true);
        checkAccessToken(doubleResponseLogin);
        checkRefreshToken(doubleResponseLogin);
    }


    @Step("Compare status code")
    public void compareStatusCode(ValidatableResponse response, int code) {
        response.statusCode(code);
    }

    @Step("Compare status")
    public void compareStatus(ValidatableResponse response,boolean successStatus){
        response.assertThat().body("success", equalTo(successStatus));
    }

    @Step("Compare accessToken not null")
    public void checkAccessToken(ValidatableResponse response){
        response.assertThat().body("accessToken", notNullValue());
    }

    @Step("Compare refreshToken not null")
    public void checkRefreshToken(ValidatableResponse response){
        response.assertThat().body("refreshToken", notNullValue());
    }

    @Step("Check email in response")
    public void checkUserEmail(ValidatableResponse response, String email){
        response.assertThat().body("user.email", equalTo(email));
    }

    @Step("Check name in response")
    public void checkUserName(ValidatableResponse response, String name){
        response.assertThat().body("user.name", equalTo(name));
    }

}
