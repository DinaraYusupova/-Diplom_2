import checkResponse.UserVerification;
import client.UserClient;
import dataGenerator.CredentialsGenerator;
import models.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class LoginUserErrorTest {
    UserVerification checkResponse = new UserVerification();
    private Credentials credentials;
    private UserClient userClient;
    private int statusCode = SC_UNAUTHORIZED;
    private final static String MESSAGE_FOR_BAD_REQUEST = "email or password are incorrect";

    public LoginUserErrorTest(Credentials credentials) {
        this.credentials = credentials;
    }

    @Before
    public void setUp(){
        userClient = new UserClient();
    }


    @Parameterized.Parameters
    public static Object[][] setData() {
        return new Object[][] {
                {CredentialsGenerator.getDataWithoutLogin()},
                {CredentialsGenerator.getDataWithoutPassword()},
                {CredentialsGenerator.getNewData()},
        };
    }
    @Test
    @DisplayName("Login user with non-existent data and check status code and response data")
    public void loginUserWithErrorData() throws InterruptedException {
        ValidatableResponse responseLogin = userClient.login(credentials);
        checkResponse.compareStatusCode(responseLogin, statusCode);
        checkResponse.compareStatus(responseLogin,false);
        checkResponse.compareResponseMessage(responseLogin, MESSAGE_FOR_BAD_REQUEST);
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
    }


}
