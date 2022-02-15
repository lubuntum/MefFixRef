package com.example.memfixref.ui.mainfragments.settings;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends AndroidViewModel {
    public static final String SETTINGS_STORAGE = "AppSettings";

    public static final String THEME = "ThemeKey";
    public static final String BLACK_THEME = "Black";
    public static final String WHITE_THEME = "White";//Тут можно сделать путь до реальной темы

    public static final String STATISTICS = "StatisticsKey";

    private SharedPreferences settingsPreferences;
    private SharedPreferences.Editor editor;
    public SettingsViewModel(Application app){
        super(app);
        settingsPreferences = getApplication().getSharedPreferences(
                SETTINGS_STORAGE, Context.MODE_PRIVATE);
        editor = settingsPreferences.edit();
    }
    public void setTheme(String theme){
        editor.putString(THEME,theme);
        editor.apply();
    }
    //Если нет то по дефолту белая
    public String getTheme(){
        return settingsPreferences.getString(THEME,WHITE_THEME);
    }
    public void setStatistics(boolean isAllowed){
        editor.putBoolean(STATISTICS,isAllowed);
        editor.apply();
    }
    //Если нет по дефолту true
    public boolean isStatisticsAllowed(){
        return settingsPreferences.getBoolean(STATISTICS,true);
    }

}
