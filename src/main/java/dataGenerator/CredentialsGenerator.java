package dataGenerator;

import models.Credentials;

import java.sql.Timestamp;

public class CredentialsGenerator {
    public static Credentials getDefault(){
        return new Credentials("loginfortest@yandex.ru","123456");
    }
    public static Credentials getDataWithoutLogin(){
        return new Credentials("","123456");
    }
    public static Credentials getDataWithoutPassword(){
        return new Credentials("loginfortest@yandex.ru","");
    }
    public static Credentials getNewData(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String login = "apitest"+ Long.toString(timestamp.getTime()) + "@yandex.ru";
        return new Credentials(login,"123456");
    }

}
