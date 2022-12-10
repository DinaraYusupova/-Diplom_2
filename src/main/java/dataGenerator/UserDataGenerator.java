package dataGenerator;

import models.User;
import models.UserData;

import java.sql.Timestamp;

public class UserDataGenerator {
//    public static UserData getDefault(){
//        return new UserData("logintestssss@yandex.ru","123456");
//    }
    public static UserData getDefault(User user){
        return UserData.from(user);
    }

    public static UserData getNewEmail(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String email = "apitest"+ Long.toString(timestamp.getTime()) + "@yandex.ru";
        return new UserData(email,"123456");
    }
    public static UserData getNewName(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String name = "apitest"+ Long.toString(timestamp.getTime());
        return new UserData("logintestssss@yandex.ru",name);
    }
}
