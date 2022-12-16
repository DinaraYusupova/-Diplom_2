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


public class GetOrderTest {
    private final OrderVerification orderResponseVerification = new OrderVerification();
    private UserClient userClient;
    String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }


    @Test
    @DisplayName("Get order with authorization and check status code and response data")
    public void getListOfOrdersWithAuthorization() {
        //Создаю в базе пользователя, получаю его токен и создаю заказ для него
        OrderClient orderClient = new OrderClient();
        User user = UserGenerator.getDefault();
        userClient.create(user); // создаю пользователя
        Credentials credentials = Credentials.from(user);
        ValidatableResponse responseLogin = userClient.login(credentials);//логин, чтобы получить accessToken
        accessToken = responseLogin.extract().path("accessToken");
        OrderData orderData = OrderDataGenerator.getRandomIngredients();//создаю заказ для пользователя
        orderClient.createOrder(accessToken,orderData);
        //Запрашиваю список заказов для пользователя и проверяю данные ответа
        ValidatableResponse responseGetOrders = userClient.getListOfOrders(accessToken);
        orderResponseVerification.compareStatusCode(responseGetOrders,SC_OK);
        orderResponseVerification.compareStatus(responseGetOrders,true);
        orderResponseVerification.checkOrders(responseGetOrders);
        orderResponseVerification.checkGetOrderId(responseGetOrders);
        orderResponseVerification.checkGetOrderIngredients(responseGetOrders);
        orderResponseVerification.compareGetOrderStatus(responseGetOrders);
        orderResponseVerification.checkOrderName(responseGetOrders);
        orderResponseVerification.checkCreatedAt(responseGetOrders);
        orderResponseVerification.checkUpdatedAt(responseGetOrders);
        orderResponseVerification.checkGetOrderNumber(responseGetOrders);
        orderResponseVerification.checkOrderTotal(responseGetOrders);
        orderResponseVerification.checkOrderTotalToday(responseGetOrders);
    }


}
