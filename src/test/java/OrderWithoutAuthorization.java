import checkResponse.OrderVerification;
import client.OrderClient;
import client.UserClient;
import dataGenerator.OrderDataGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.OrderData;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class OrderWithoutAuthorization {
    private final OrderVerification orderResponseVerification = new OrderVerification();
    private OrderClient orderClient;
    private UserClient userClient;
    private final String MESSAGE = "You should be authorised";


    @Test
    @DisplayName("Create order without authorization and check status code and response data")
    public void createOrderWithoutAuthorization() {
        OrderData orderData = OrderDataGenerator.getRandomIngredients();
        orderClient = new OrderClient();
        ValidatableResponse responseCreateOrder = orderClient.createOrder("",orderData);
        orderResponseVerification.compareStatusCode(responseCreateOrder,SC_OK);
        orderResponseVerification.compareStatus(responseCreateOrder,true);
        orderResponseVerification.checkName(responseCreateOrder);
        orderResponseVerification.checkOrderNumber(responseCreateOrder);
    }

    @Test
    @DisplayName("Get order without authorization and check status code and response data")
    public void getListOfOrdersWithoutAuthorization() {
        userClient = new UserClient();
        ValidatableResponse responseGetOrders = userClient.getListOfOrders("");
        orderResponseVerification.compareStatusCode(responseGetOrders,SC_UNAUTHORIZED);
        orderResponseVerification.compareStatus(responseGetOrders,false);
        orderResponseVerification.compareResponseMessage(responseGetOrders, MESSAGE);
    }
}
