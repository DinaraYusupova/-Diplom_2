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
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class GetOrderTest {
    private final OrderVerification orderResponseVerification = new OrderVerification();
    private UserClient userClient;
    private final String MESSAGE = "You should be authorised";

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Get order with authorization and check status code and response data")
    public void getListOfOrdersWithAuthorization() throws InterruptedException {
        //Создаю в базе пользователя, получаю его токен и создаю заказ для него
        OrderClient orderClient = new OrderClient();
        User user = UserGenerator.getDefault();
        userClient.create(user); // создаю пользователя
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        Credentials credentials = Credentials.from(user);
        ValidatableResponse responseLogin = userClient.login(credentials);//логин, чтобы получить accessToken
        String accessToken = responseLogin.extract().path("accessToken");
        OrderData orderData = OrderDataGenerator.getRandomIngredients();//создаю заказ для пользователя
        orderClient.createOrder(accessToken,orderData);
        //Запрашиваю список заказов для пользователя и проверяю данные ответа
        ValidatableResponse responseGetOrders = userClient.getListOfOrders(accessToken);
        orderResponseVerification.compareStatusCode(responseGetOrders,SC_OK);
        orderResponseVerification.compareStatus(responseGetOrders,true);
        orderResponseVerification.checkOrders(responseGetOrders);
        orderResponseVerification.checkOrderTotal(responseGetOrders);
        orderResponseVerification.checkOrderTotalToday(responseGetOrders);
        //удаляю пользователя
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Get order without authorization and check status code and response data")
    public void getListOfOrdersWithoutAuthorization() {
        ValidatableResponse responseGetOrders = userClient.getListOfOrders("");
        orderResponseVerification.compareStatusCode(responseGetOrders,SC_UNAUTHORIZED);
        orderResponseVerification.compareStatus(responseGetOrders,false);
        orderResponseVerification.compareResponseMessage(responseGetOrders, MESSAGE);
    }

}
