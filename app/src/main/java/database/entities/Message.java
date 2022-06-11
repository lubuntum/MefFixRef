package database.entities;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("message")
    public String message;
    public Message(String message){
        this.message = message;
    }
}
