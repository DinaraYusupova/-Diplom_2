import checkResponse.UserVerification;
import client.UserClient;
import dataGenerator.UserGenerator;
import models.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;

@RunWith(Parameterized.class)
public class CreateUserWithoutDataTest {
    UserVerification checkResponse = new UserVerification();
    private final User user;
    private UserClient userClient;
    private final static String MESSAGE_FOR_BAD_REQUEST = "Email, password and name are required fields";

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    public CreateUserWithoutDataTest(User user) {
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] setData() {
        return new Object[][] {
                { UserGenerator.getDataWithoutLogin()},
                { UserGenerator.getDataWithoutPassword()},
                { UserGenerator.getDataWithoutFirstName()},
        };
    }

    @Test
    @DisplayName("Check status code and response data of creating user with insufficient data")
    public void createUserWithInsufficientData() throws InterruptedException {
        ValidatableResponse responseCreate = userClient.create(user);
        checkResponse.compareStatusCode(responseCreate, SC_FORBIDDEN);
        checkResponse.compareStatus(responseCreate,false);
        checkResponse.compareResponseMessage(responseCreate, MESSAGE_FOR_BAD_REQUEST);
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
    }
}
