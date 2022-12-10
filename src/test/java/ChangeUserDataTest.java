import checkResponse.UserVerification;
import client.UserClient;
import dataGenerator.UserDataGenerator;
import dataGenerator.UserGenerator;
import models.Credentials;
import models.User;
import models.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@RunWith(Parameterized.class)
public class ChangeUserDataTest {
    UserVerification checkResponse;
    private final UserData userData;
    private UserClient userClient;
    private String accessToken;
    String ERROR_MESSAGE = "You should be authorised";

    public ChangeUserDataTest(UserData userData) {
        this.userData = userData;
    }

    @Before
    public void createUser() throws InterruptedException {
        userClient = new UserClient();
        User user = UserGenerator.getDefault();
        userClient.create(user); // создаю пользователя, чтобы он точно был в базе
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        Credentials credentials = Credentials.from(user);
        ValidatableResponse responseLogin = userClient.login(credentials);//логин, чтобы получить accessToken
        accessToken = responseLogin.extract().path("accessToken");
        checkResponse = new UserVerification();
    }
    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    @Parameterized.Parameters
    public static Object[][] setData() {
        return new Object[][] {
                {UserDataGenerator.getNewEmail()},
                {UserDataGenerator.getNewName()},
        };
    }

    @Test
    @DisplayName("change user data and check statusCode and response data")
    public void changeUserDataTest() {
        ValidatableResponse responseChangeData = userClient.changeUserData(accessToken,userData);
        checkResponse.compareStatusCode(responseChangeData,SC_OK);
        checkResponse.compareStatus(responseChangeData,true);
        checkResponse.compareUserEmail(responseChangeData,userData.getEmail());
        checkResponse.compareUserName(responseChangeData,userData.getName());
    }

    @Test
    @DisplayName("try to change user data without authorization and check statusCode and response data")
    public void changeDataWithoutAuthorizationTest() {
        String accessTokenError = "";
        ValidatableResponse responseChangeData = userClient.changeUserData(accessTokenError,userData);
        checkResponse.compareStatusCode(responseChangeData,SC_UNAUTHORIZED);
        checkResponse.compareStatus(responseChangeData,false);
        checkResponse.compareResponseMessage(responseChangeData, ERROR_MESSAGE);
    }
}
