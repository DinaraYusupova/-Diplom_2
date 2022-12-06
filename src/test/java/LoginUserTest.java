import checkResponse.UserVerification;
import client.UserClient;
import dataGenerator.CredentialsGenerator;
import dataGenerator.UserGenerator;
import models.Credentials;
import models.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class LoginUserTest {
    UserVerification checkResponse = new UserVerification();
    private User user;
    private Credentials credentials;
    private UserClient userClient;
    private final int statusCode = SC_OK;
    private String accessToken;
    private ValidatableResponse responseLogin;


    @Before
    public void setUp() throws InterruptedException {
        userClient = new UserClient();
        user = UserGenerator.getDefault();
        credentials = CredentialsGenerator.getDefault();
        userClient.create(user); // создаю пользователя
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        responseLogin = userClient.login(credentials);
        accessToken = responseLogin.extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Login user and check statusCode and response data")
    public void loginUser() {
        checkResponse.compareStatusCode(responseLogin, statusCode);
        checkResponse.compareStatus(responseLogin,true);
        checkResponse.checkAccessToken(responseLogin);
        checkResponse.checkRefreshToken(responseLogin);
        checkResponse.compareUserEmail(responseLogin,user.getEmail());
        checkResponse.compareUserName(responseLogin,user.getName());
    }

    @Test
    @DisplayName("Double login user and check statusCode and status") //Тест с попыткой повторного логина, с уже залогининным пользователем
    public void doubleLoginUser() throws InterruptedException {
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        ValidatableResponse doubleResponseLogin = userClient.login(credentials);
        checkResponse.compareStatusCode(doubleResponseLogin, statusCode);
        checkResponse.compareStatus(doubleResponseLogin,true);
    }

}
