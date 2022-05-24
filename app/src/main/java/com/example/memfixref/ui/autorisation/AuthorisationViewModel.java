package com.example.memfixref.ui.autorisation;

import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.EMAIL;
import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.FAVORITE_TAGS;
import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.SETTINGS_STORAGE;
import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.STATISTICS;
import static com.example.memfixref.ui.mainfragments.settings.SettingsViewModel.USERNAME;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class AuthorisationViewModel extends AndroidViewModel {
    private SharedPreferences settingsPreferences;
    private SharedPreferences.Editor editor;

    public AuthorisationViewModel(Application app){
        super(app);
        settingsPreferences = getApplication().getSharedPreferences(
                SETTINGS_STORAGE, Context.MODE_PRIVATE);
        editor = settingsPreferences.edit();
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
}
