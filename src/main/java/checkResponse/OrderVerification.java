package checkResponse;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderVerification {

    @Step("Compare status code")
    public void compareStatusCode(ValidatableResponse response, int code){
        response.statusCode(code);
    }

    @Step("Compare success status")
    public void compareStatus(ValidatableResponse response,boolean successStatus){
        response.assertThat().body("success", equalTo(successStatus));
    }

    @Step("Check name")
    public void checkName(ValidatableResponse response){
        response.assertThat().body("name", notNullValue());
    }

    @Step("Check order number")
    public void checkOrderNumber(ValidatableResponse response){
        response.assertThat().body("order.number", notNullValue());
    }
    @Step("Check order ingredients")
    public void checkOrderIngredients(ValidatableResponse response){
        response.assertThat().body("order.ingredients", notNullValue());
    }
    @Step("Check order id")
    public void checkOrderId(ValidatableResponse response){
        response.assertThat().body("order._id", notNullValue());
    }
    @Step("Compare order user name")
    public void compareUserName(ValidatableResponse response,String name){
        response.assertThat().body("order.owner.name", equalTo(name));
    }
    @Step("Compare order user email")
    public void compareUserEmail(ValidatableResponse response,String email){
        response.assertThat().body("order.owner.email", equalTo(email));
    }

    @Step("Compare response message")
    public void compareResponseMessage(ValidatableResponse response, String message){
        response.assertThat().body("message", equalTo(message));
    }

    @Step("Check orders")
    public void checkOrders(ValidatableResponse response){
        response.assertThat().body("orders", notNullValue());
    }
    @Step("Check order total")
    public void checkOrderTotal(ValidatableResponse response){
        response.assertThat().body("total", notNullValue());
    }
    @Step("Check order totalToday")
    public void checkOrderTotalToday(ValidatableResponse response){
        response.assertThat().body("totalToday", notNullValue());
    }
}
