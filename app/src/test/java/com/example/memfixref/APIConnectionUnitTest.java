package com.example.memfixref;

import org.junit.Test;

import database.entities.Message;
import database.entities.User;
import database.web.APIAccountServices;
import database.web.WebRepository;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class APIConnectionUnitTest {
    @Test
    public void ping() throws IOException {
        APIAccountServices apiAccountServices = WebRepository.getRetrofitInstance().create(APIAccountServices.class);
        final String[] connection = {""};

        Response<Message> response = apiAccountServices.ping().execute();
        if (response.isSuccessful()){
            connection[0] = response.body().message;
            assertEquals("ping", connection[0]);
        }
        else {
            assertEquals("failed",response.message());
        }

    }
    @Test
    public void userRegistration() throws IOException {
        User user = new User("UnitTestLogin","UnitTest@gmail.com");
        String message = "";
        APIAccountServices apiAccountServices = WebRepository.getRetrofitInstance().create(APIAccountServices.class);
        Response<Message> response = apiAccountServices.userRegistration(user).execute();
        if (response.isSuccessful())
            message = response.body().message;
        //assertEquals("ok",message);
        assertEquals("busy",message);
    }

}
