import checkResponse.UserVerification;
import client.UserClient;
import dataGenerator.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.User;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class CreateUserWithWrongDataTest {
    UserVerification checkResponse = new UserVerification();
    private User user;
    private UserClient userClient;

    @Test
    @DisplayName("Check status code and response data of creating user with wrong email")
    public void createUserWithWrongEmail() {
        userClient = new UserClient();
        user = UserGenerator.getDataWithWrongLogin();
        ValidatableResponse responseCreate = userClient.create(user);
        checkResponse.compareStatusCode(responseCreate,SC_INTERNAL_SERVER_ERROR);
    }
}
