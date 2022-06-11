package com.example.memfixref.ui.autorisation;

import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.EMAIL;
import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.FAVORITE_TAGS;
import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.SETTINGS_STORAGE;
import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.STATISTICS;
import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.USERNAME;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import database.entities.User;
import database.web.APIAccountServices;
import database.web.WebRepository;

public class AuthorisationViewModel extends AndroidViewModel {
    private SharedPreferences settingsPreferences;
    private SharedPreferences.Editor editor;
    private APIAccountServices apiAccountServices;
    private User user;
    //При успешной регистрации данные сохраняются локально
    private MutableLiveData<String> registrationLiveData;

    public AuthorisationViewModel(Application app){
        super(app);
        settingsPreferences = getApplication().getSharedPreferences(
                SETTINGS_STORAGE, Context.MODE_PRIVATE);
        editor = settingsPreferences.edit();

        registrationLiveData = new MutableLiveData<>();
        apiAccountServices = WebRepository.getRetrofitInstance().create(APIAccountServices.class);
    }
    public void setName(String name){
        editor.putString(USERNAME,name);
        editor.apply();
    }
    public void setEmail(String email){
        editor.putString(EMAIL,email);
        editor.apply();
    }
    public void setTags(String tags){
        editor.putString(FAVORITE_TAGS,tags);
        editor.apply();
    }
    public void setDataUsagePermission(boolean isAllow){
        editor.putBoolean(STATISTICS,isAllow);
        editor.apply();
    }
    public MutableLiveData<String> getRegistrationLiveData(){
        return this.registrationLiveData;
    }
    public User getUser(){
        return this.user;
    }
    public APIAccountServices getApiServices(){
        return apiAccountServices;
    }
    public boolean createUser(@NonNull String login, @NonNull String email){
        String emailRegex = "^[A-Za-z\\d_]{0,25}@[A-Za-z]{2,8}\\.[a-z]{2,8}$";
        if (login.matches("[A-Za-z\\d]{2,25}") && email.matches(emailRegex))  {
            this.user = new User(login,email);
            return true;
        }
        return false;
    }


}
