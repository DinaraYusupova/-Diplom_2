package models;


//Модель, для хранения данных пользователя, необходимых для авторизации (используется для сериализации данных при авторизации)
public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public static Credentials from(User user){
        return new Credentials(user.getEmail(),user.getPassword());
    }
    public Credentials() {
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
