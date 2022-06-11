package database.entities;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login")
    private String login;
    @SerializedName("email")
    private String email;
    public User(){}
    public User(String login, String email){
        this.login = login;
        this.email = email;
    }
}
