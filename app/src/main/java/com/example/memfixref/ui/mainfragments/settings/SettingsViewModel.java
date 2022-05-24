package com.example.memfixref.ui.mainfragments.settings;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends AndroidViewModel {
    public static final String SETTINGS_STORAGE = "AppSettings";

    public static final String THEME = "ThemeKey";
    public static final String BLACK_THEME = "Black";
    public static final String WHITE_THEME = "White";//Тут можно сделать путь до реальной темы

    public static final String STATISTICS = "StatisticsKey";

    public static final String USERNAME = "Username";
    public static final String QUOTE = "Userquote";
    public static final String EMAIL = "UserEmail";

    public static final String FAVORITE_TAGS = "Tags";

    public static final String KEY_VALUE_SESSION_TIME = "Key_value_session_time";
    public static final String RELATIVE_LISTS_SESSION_TIME = "Relative_lists_session_time";
    public static final String RANDOM_LISTS_SESSION_TIME = "Random_lists_session_time";

    private SharedPreferences settingsPreferences;
    private SharedPreferences.Editor editor;

    private MutableLiveData<Boolean> updateHintsLiveData;
    public SettingsViewModel(Application app){
        super(app);
        settingsPreferences = getApplication().getSharedPreferences(
                SETTINGS_STORAGE, Context.MODE_PRIVATE);
        editor = settingsPreferences.edit();
        updateHintsLiveData = new MutableLiveData<>();
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

    public SharedPreferences getSettingsPreferences() {
        return settingsPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public MutableLiveData<Boolean> getUpdateHintsLiveData() {
        return updateHintsLiveData;
    }

    public void setUpdateHintsLiveData(MutableLiveData<Boolean> updateHintsLiveData) {
        this.updateHintsLiveData = updateHintsLiveData;
    }
}
