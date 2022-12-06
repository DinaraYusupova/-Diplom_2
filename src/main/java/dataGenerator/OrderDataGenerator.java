package dataGenerator;

import client.OrderClient;
import models.OrderData;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderDataGenerator {
    public static OrderData getRandomIngredients() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse getResponce = orderClient.getIngredients();
        List<String> ingredientIds = getResponce.extract().jsonPath().getList("data._id");

        List<String> listOfIngredients = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            int randomIndex = rand.nextInt(ingredientIds.size() - 1);
            String randomElement = ingredientIds.get(randomIndex);
            listOfIngredients.add(randomElement);
        }
        return new OrderData(listOfIngredients);
    }
    public static OrderData getWithoutIngredients222() {
        return new OrderData();
    }
    public static OrderData getWithoutIngredients() {
        List<String> listOfIngredients = new ArrayList<>();
        return new OrderData(listOfIngredients);
    }
    public static OrderData getWithWrongIngredients() {
        List<String> listOfIngredients = new ArrayList<>();
        listOfIngredients.add("60d3b41abdacab0026a733c6");
        listOfIngredients.add("111111111111111111111111");
        return new OrderData(listOfIngredients);
    }
}
