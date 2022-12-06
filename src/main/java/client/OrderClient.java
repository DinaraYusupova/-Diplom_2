package client;

import models.OrderData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client{
    private  static final String PATH_GET_INGREDIENTS = "/api/ingredients";
    private  static final String PATH_CREATE_ORDER = "/api/orders";


    @Step("get ingredients")
    public ValidatableResponse getIngredients(){
        return given()
                .spec(getSpec())
                .log().all()
                .get(PATH_GET_INGREDIENTS)
                .then()
                .log().all();
    }


    @Step("create order")
    public ValidatableResponse createOrder(String accessToken, OrderData orderData){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all()
                .body(orderData)
                .when()
                .post(PATH_CREATE_ORDER)
                .then()
                .log().all();
    }


}
