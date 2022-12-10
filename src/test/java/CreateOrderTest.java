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

//Тесты на создание заказа с авторизацией и без.
//Не стала прикручивать параметризацию, т.к. приходять в ответ очень разные json, и решила сделать разный список проверок
public class CreateOrderTest {
    private final OrderVerification orderResponseVerification = new OrderVerification();
    private OrderClient orderClient;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Create order with authorization and check status code and response data")
    public void createOrderWithAuthorization() throws InterruptedException {
        //Создаю в базе пользователя и получаю его токен
        UserClient userClient = new UserClient();
        User user = UserGenerator.getDefault();
        userClient.create(user); // создаю пользователя, чтобы он точно был в базе
        Thread.sleep(3000); //в приложении уязвимость: если отправлять 2 запроса подряд, то появляется ошибка 429 Too Many Requests
        Credentials credentials = Credentials.from(user);
        ValidatableResponse responseLogin = userClient.login(credentials);//логин, чтобы получить accessToken
        String accessToken = responseLogin.extract().path("accessToken");
        //Создаю заказ из 4 рандомных ингридиентов
        OrderData orderData = OrderDataGenerator.getRandomIngredients();
        ValidatableResponse responseCreateOrder = orderClient.createOrder(accessToken,orderData);
        //Проверяю выборочный список параметров из json response, что проверять наверное нужно согласовать с требованиями
        orderResponseVerification.compareStatusCode(responseCreateOrder,SC_OK);
        orderResponseVerification.compareStatus(responseCreateOrder,true);
        orderResponseVerification.checkName(responseCreateOrder);
        orderResponseVerification.checkOrderNumber(responseCreateOrder);
        orderResponseVerification.checkOrderIngredients(responseCreateOrder);
        orderResponseVerification.checkOrderId(responseCreateOrder);
        orderResponseVerification.compareUserName(responseCreateOrder,user.getName());
        orderResponseVerification.compareUserEmail(responseCreateOrder,user.getEmail());
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Create order without authorization and check status code and response data")
    public void createOrderWithoutAuthorization() {
        OrderData orderData = OrderDataGenerator.getRandomIngredients();;
        ValidatableResponse responseCreateOrder = orderClient.createOrder("",orderData);
        orderResponseVerification.compareStatusCode(responseCreateOrder,SC_OK);
        orderResponseVerification.compareStatus(responseCreateOrder,true);
        orderResponseVerification.checkName(responseCreateOrder);
        orderResponseVerification.checkOrderNumber(responseCreateOrder);
    }

}
