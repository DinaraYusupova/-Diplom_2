import checkResponse.UserVerification;
import client.UserClient;
import dataGenerator.UserGenerator;
import models.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;


public class CreateUserTest {
    UserVerification checkResponse = new UserVerification();
    private User user;
    private UserClient userClient;
    private String accessToken;
    private final static String MESSAGE_FOR_CONFLICT = "User already exists";

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }


    @Test
    @DisplayName("Check status code and response data of creating user")
    public void createNewUser() {
        user = UserGenerator.getNewData();
        ValidatableResponse responseCreate = userClient.create(user);
        checkResponse.compareStatusCode(responseCreate,SC_OK);
        checkResponse.compareStatus(responseCreate,true);
        checkResponse.checkAccessToken(responseCreate);
        checkResponse.checkRefreshToken(responseCreate);
        accessToken = responseCreate.extract().path("accessToken");
        checkResponse.compareUserEmail(responseCreate,user.getEmail());
        checkResponse.compareUserName(responseCreate,user.getName());
    }

    @Test
    @DisplayName("Check status code and response data of creating double user")
    public void createDoubleUser() throws InterruptedException {
        user = UserGenerator.getNewData();
        ValidatableResponse responseCreate = userClient.create(user); //создаем нового пользователя
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        ValidatableResponse doubleResponseCreate = userClient.create(user); //создаем еще одного пользователя с теми же данными
        checkResponse.compareStatusCode(doubleResponseCreate,SC_FORBIDDEN);
        checkResponse.compareStatus(doubleResponseCreate,false);
        checkResponse.compareResponseMessage(doubleResponseCreate, MESSAGE_FOR_CONFLICT);
        accessToken = responseCreate.extract().path("accessToken");
    }

    @Test
    @DisplayName("Check status code and response data of creating user with wrong email")
    public void createUserWithWrongEmail() {
        user = UserGenerator.getDataWithWrongLogin();
        ValidatableResponse responseCreate = userClient.create(user);
        checkResponse.compareStatusCode(responseCreate,SC_INTERNAL_SERVER_ERROR);
    }


}
