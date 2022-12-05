import Client.UserClient;
import DataGenerator.CredentialsGenerator;
import Models.Credentials;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginUserErrorTest {
    private Credentials credentials;
    private UserClient userClient;
    private int statusCode = SC_UNAUTHORIZED;
    private final static String messageForBadRequest = "email or password are incorrect";



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
    @DisplayName("login user with non-existent data")
    public void loginUserWithErrorData() throws InterruptedException {
        ValidatableResponse responseLogin = userClient.login(credentials);
        compareStatusCode(responseLogin, statusCode);
        compareStatus(responseLogin,false);
        compareResponseMessage(responseLogin,messageForBadRequest);
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
