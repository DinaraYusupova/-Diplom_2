package client;

import models.User;
import models.Credentials;
import models.UserData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client{
    private  static final String PATH_CREATE = "/api/auth/register";
    private  static final String PATH_LOGIN = "/api/auth/login";
    private  static final String PATH_DELETE_AND_CHANGE = "/api/auth/user";
    private  static final String PATH_GET_LIST_OF_ORDERS = "/api/orders";

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
                .delete(PATH_DELETE_AND_CHANGE)
                .then()
                .log().all();
    }

    @Step("Change user data")
    public ValidatableResponse changeUserData(String accessToken, UserData userData){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all()
                .body(userData)
                .when()
                .patch(PATH_DELETE_AND_CHANGE)
                .then()
                .log().all();
    }

    @Step("get list of orders")
    public ValidatableResponse getListOfOrders(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all()
                .get(PATH_GET_LIST_OF_ORDERS)
                .then()
                .log().all();
    }
}
