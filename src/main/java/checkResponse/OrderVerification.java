package checkResponse;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


//Методы для проверки ответов при создании заказа
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




    public void checkOrderCreatedData(ValidatableResponse response){
        response.assertThat().body("order.createdAt", notNullValue());
    }
    public void checkOrderUpdatedData(ValidatableResponse response){
        response.assertThat().body("order.updatedAt", notNullValue());
    }
    public void checkOrderPrice(ValidatableResponse response){
        response.assertThat().body("order.price", notNullValue());
    }

    public void compareResponseOrderStatus(ValidatableResponse response){
        response.assertThat().body("order.status", equalTo("done"));
    }

    public void checkOrderIngredientsId(ValidatableResponse response){
        response.assertThat().body("order.ingredients._id", notNullValue());
    }


    public void checkOrderIngredientsName(ValidatableResponse response){
        response.assertThat().body("order.ingredients.name", notNullValue());
    }
    public void checkOrderIngredientsType(ValidatableResponse response){
        response.assertThat().body("order.ingredients.type", notNullValue());
    }
    public void checkOrderIngredientsProteins(ValidatableResponse response){
        response.assertThat().body("order.ingredients.proteins", notNullValue());
    }
    public void checkOrderIngredientsFat(ValidatableResponse response){
        response.assertThat().body("order.ingredients.fat", notNullValue());
    }
    public void checkOrderIngredientsCarbohydrates(ValidatableResponse response){
        response.assertThat().body("order.ingredients.carbohydrates", notNullValue());
    }
    public void checkOrderIngredientsCalories(ValidatableResponse response){
        response.assertThat().body("order.ingredients.calories", notNullValue());
    }
    public void checkOrderIngredientsPrice(ValidatableResponse response){
        response.assertThat().body("order.ingredients.price", notNullValue());
    }
    public void checkOrderIngredientsImage(ValidatableResponse response){
        response.assertThat().body("order.ingredients.image", notNullValue());
    }
    public void checkOrderIngredientsImage_mobile(ValidatableResponse response){
        response.assertThat().body("order.ingredients.image_mobile", notNullValue());
    }
    public void checkOrderIngredientsImage_large(ValidatableResponse response){
        response.assertThat().body("order.ingredients.image_large", notNullValue());
    }
    public void checkOrderIngredientsV(ValidatableResponse response){
        response.assertThat().body("order.ingredients.__v", notNullValue());
    }





    public void checkGetOrderId(ValidatableResponse response){
        response.assertThat().body("orders._id", notNullValue());
    }


    public void checkGetOrderIngredients(ValidatableResponse response){
        response.assertThat().body("orders.ingredients", notNullValue());
    }

    public void compareGetOrderStatus(ValidatableResponse response){
        response.assertThat().body("orders.status", notNullValue());
    }
    public void checkOrderName(ValidatableResponse response){
        response.assertThat().body("orders.name", notNullValue());
    }
    public void checkCreatedAt(ValidatableResponse response){
        response.assertThat().body("orders.createdAt", notNullValue());
    }
    public void checkUpdatedAt(ValidatableResponse response){
        response.assertThat().body("orders.updatedAt", notNullValue());
    }
    public void checkGetOrderNumber(ValidatableResponse response){
        response.assertThat().body("orders.number", notNullValue());
    }
}
