package DataGenerator;

import Models.Credentials;
import Models.User;

import java.sql.Timestamp;

public class UserGenerator {
    public static User getNewData(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String login = "apitest"+ Long.toString(timestamp.getTime()) + "@yandex.ru";
        return new User(login,"123456","chebyrashka");
    }

    public static User getDataWithoutLogin() {
        return new User("","123456","chebyrashka");
    }

    public static User getDataWithWrongLogin() {
        return new User("apitest","123456","chebyrashka");
    }

    public static User getDataWithoutPassword() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String login = "apitest"+ Long.toString(timestamp.getTime()) + "@yandex.ru";
        return new User(login,"","chebyrashka");
    }

    public static User getDataWithoutFirstName() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String login = "apitest"+ Long.toString(timestamp.getTime()) + "@yandex.ru";
        return new User(login,"123456","");
    }

    public static User getDefault(){
        return new User("loginapitest@yandex.ru","123456","chebyrashka");
    }
}
