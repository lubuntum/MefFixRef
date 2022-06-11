package database.web;

import database.entities.Message;
import database.entities.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIAccountServices {

    @POST("api/registration")
    Call<Message> userRegistration(@Body User user);
    @POST("api/account-is-valid")
    Call<Message> accountIsValid(@Body User user);

    @POST("api/ping")
    Call<Message> ping();

}
