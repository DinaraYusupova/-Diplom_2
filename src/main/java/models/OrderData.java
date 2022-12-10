package models;

import java.util.List;

//Модель, для хранения данных для заказа бургера (используется для сериализации данных при заказе)
public class OrderData {
    private List<String> ingredients;

    public OrderData(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public OrderData() {}

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
