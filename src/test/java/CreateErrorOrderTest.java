import checkResponse.OrderVerification;
import client.OrderClient;
import dataGenerator.OrderDataGenerator;
import models.OrderData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(Parameterized.class)
public class CreateErrorOrderTest {
    private final OrderVerification orderResponseVerification = new OrderVerification();
    private OrderClient orderClient;
    private final OrderData orderData;
    private final String message;
    private final static String MESSAGE_FOR_WRONG_INGREDIENTS = "One or more ids provided are incorrect";
    private final static String MESSAGE_FOR_NULL_INGREDIENTS = "Ingredient ids must be provided";

    public CreateErrorOrderTest(OrderData orderData, String message) {
        this.orderData = orderData;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] setData() {
        return new Object[][] {
                { OrderDataGenerator.getWithWrongIngredients(), MESSAGE_FOR_WRONG_INGREDIENTS},
                { OrderDataGenerator.getWithoutIngredients(), MESSAGE_FOR_NULL_INGREDIENTS},
        };
    }


    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }


    @Test
    @DisplayName("Check status code, status and message of creating order with wrong data")
    public void createWrongOrder() {
        ValidatableResponse responseCreateOrder = orderClient.createOrder("",orderData);
        orderResponseVerification.compareStatusCode(responseCreateOrder,SC_BAD_REQUEST);
        orderResponseVerification.compareStatus(responseCreateOrder,false);
        orderResponseVerification.compareResponseMessage(responseCreateOrder, message);
    }

}
