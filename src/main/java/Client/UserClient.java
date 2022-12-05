package Client;

import Models.User;
import Models.Credentials;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client{
    private  static final String PATH_CREATE = "/api/auth/register";
    private  static final String PATH_LOGIN = "/api/auth/login";
    private  static final String PATH_DELETE = "/api/auth/user";

    @Step("Create user")
    public ValidatableResponse create(User user){
        return given()
                .spec(getSpec())
                .log().all()
                .body(user)
                .when()
                .post(PATH_CREATE)
                .then()
                .log().all();
    }
    @Step("Login user")
    public ValidatableResponse login(Credentials credentials){
        return given()
                .spec(getSpec())
                .log().all()
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then()
                .log().all();
    }
    @Step("Delete user")
    public ValidatableResponse delete(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all()
                .when()
                .delete(PATH_DELETE)
                .then()
                .log().all();
    }
}
