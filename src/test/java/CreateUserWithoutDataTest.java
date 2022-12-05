import Client.UserClient;
import DataGenerator.UserGenerator;
import Models.User;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateUserWithoutDataTest {
    private User user;
    private UserClient userClient;
    private String accessToken;
    private final static String messageForBadRequest = "Email, password and name are required fields";

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
    @DisplayName("Check status code and message of creating courier with insufficient data")
    public void createUserWithInsufficientData() throws InterruptedException {
        ValidatableResponse responseCreate = userClient.create(user);
        compareStatusCode(responseCreate, SC_FORBIDDEN);
        compareStatus(responseCreate,false);
        compareResponseMessage(responseCreate,messageForBadRequest);
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
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
}
