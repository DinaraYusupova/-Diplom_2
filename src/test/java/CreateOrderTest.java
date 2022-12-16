import checkResponse.OrderVerification;
import client.OrderClient;
import client.UserClient;
import dataGenerator.OrderDataGenerator;
import dataGenerator.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import models.Credentials;
import models.OrderData;
import models.User;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;

//Не стала прикручивать параметризацию, т.к. приходять в ответ очень разные json, и решила сделать разный список проверок
public class CreateOrderTest {
    private final OrderVerification orderResponseVerification = new OrderVerification();
    private OrderClient orderClient;
    private UserClient userClient;
    private String accessToken;
    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Create order with authorization and check status code and response data")
    public void createOrderWithAuthorization(){
        //Создаю в базе пользователя и получаю его токен
        userClient = new UserClient();
        User user = UserGenerator.getDefault();
        userClient.create(user); // создаю пользователя, чтобы он точно был в базе
        Credentials credentials = Credentials.from(user);
        ValidatableResponse responseLogin = userClient.login(credentials);//логин, чтобы получить accessToken
        accessToken = responseLogin.extract().path("accessToken");
        //Создаю заказ из 4 рандомных ингридиентов
        OrderData orderData = OrderDataGenerator.getRandomIngredients();
        ValidatableResponse responseCreateOrder = orderClient.createOrder(accessToken,orderData);
        //Проверяю список параметров из json response
        orderResponseVerification.compareStatusCode(responseCreateOrder,SC_OK);
        orderResponseVerification.compareStatus(responseCreateOrder,true);
        orderResponseVerification.checkName(responseCreateOrder);
        orderResponseVerification.checkOrderNumber(responseCreateOrder);
        orderResponseVerification.checkOrderIngredients(responseCreateOrder);
        orderResponseVerification.checkOrderId(responseCreateOrder);
        orderResponseVerification.compareUserName(responseCreateOrder,user.getName());
        orderResponseVerification.compareUserEmail(responseCreateOrder,user.getEmail());
        orderResponseVerification.checkOrderCreatedData(responseCreateOrder);
        orderResponseVerification.checkOrderUpdatedData(responseCreateOrder);
        orderResponseVerification.checkOrderPrice(responseCreateOrder);
        orderResponseVerification.compareResponseOrderStatus(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsId(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsName(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsType(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsProteins(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsFat(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsCarbohydrates(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsCalories(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsPrice(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsImage(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsImage_mobile(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsImage_large(responseCreateOrder);
        orderResponseVerification.checkOrderIngredientsV(responseCreateOrder);
    }
}
