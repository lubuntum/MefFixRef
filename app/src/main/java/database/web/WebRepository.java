package database.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import database.entities.User;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
public class WebRepository {

    public APIAccountServices apiAccountServices;
    public static final String BASE_URL = "http://192.168.1.13:8000/";

    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type,typeAdapter);
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
    //for parsing response like Kit, List<Kit> etc..
    public static Retrofit getRetrofitInstanceWithConverter(Type type,Object typeAdapter){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(createGsonConverter(type, typeAdapter))
                .build();
    }
    //by default for messages from server or user registration
    public static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
    }
    // static volatile WebRepository INSTANCE = null;
    /*
    public static WebRepository getInstance(){
        if (INSTANCE == null){
            synchronized (WebRepository.class){
                if (INSTANCE == null){
                    INSTANCE = new WebRepository();
                }
            }
        }
        return INSTANCE;
    }
     */
    public WebRepository(){

    }

    public void userRegistration(User user){

    }

}
